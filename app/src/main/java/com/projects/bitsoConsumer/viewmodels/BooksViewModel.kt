package com.projects.bitsoConsumer.viewmodels
import androidx.lifecycle.viewModelScope
import com.projects.bitsoConsumer.models.BooksPayload
import com.projects.bitsoConsumer.models.DetailedPayload
import com.projects.bitsoConsumer.mvi.features.BaseViewModel
import com.projects.bitsoConsumer.mvi.features.books.MainContract
import com.projects.bitsoConsumer.repository.BitsoRepository
import com.projects.bitsoConsumer.support.icon
import com.projects.bitsoConsumer.support.shortToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val useCases: BitsoRepository,
) : BaseViewModel<MainContract.Event, MainContract.State, MainContract.Effect>() {

    init {
        setEvent(MainContract.Event.OnInit)
    }

    override fun createInitialState(): MainContract.State {
        return MainContract.State(
            MainContract.BitsoApiState.Idle,
        )
    }

    /**
     * Handle each event
     */
    override fun handleEvent(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.OnInit -> {
                getBooks()
            }
            is MainContract.Event.OnNewClick -> {
            //    getBooks()
            }
        }
    }

    private fun setMessageTicker() {
        viewModelScope.launch {
            setState {
                copy(getInfo = MainContract.BitsoApiState.tick("Tick Tac Toe"))
            }
        }
    }

    private fun info() {
        viewModelScope.launch {
            useCases.getBooks()
        }
    }

    private fun getBooks() {
        try {
            viewModelScope.launch {
                useCases.getTradeBooks().map {
                    it.filter {
                        it.book.contains("mxn")
                    }
                }
                    .collect {
                        setState { copy(getInfo = MainContract.BitsoApiState.Success(getnewList(it))) }
                    }
            }
        } catch (e: Exception) { println("aqui llegue es $e") } }
}

private fun getnewList(openedPayloads: List<BooksPayload>): List<DetailedPayload> {
    val returnlist = mutableListOf<DetailedPayload>()

    openedPayloads.forEach {
        returnlist.add(
            DetailedPayload(
                payload = it,
                shortname = shortToken(it.book),
                icon = icon(it.book),
                pair=it.book
            ),
        )
    }

    return returnlist
}
