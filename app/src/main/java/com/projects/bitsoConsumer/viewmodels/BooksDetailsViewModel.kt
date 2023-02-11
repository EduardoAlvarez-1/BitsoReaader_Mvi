package com.projects.bitsoConsumer.viewmodels

import androidx.lifecycle.viewModelScope
import com.projects.bitsoConsumer.models.trading.PayloadTrades
import com.projects.bitsoConsumer.mvi.features.BaseViewModel
import com.projects.bitsoConsumer.mvi.features.TradeInfo.DetailsContract
import com.projects.bitsoConsumer.repository.BitsoDetailsRepository
import com.projects.bitsoConsumer.support.operationKind
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksDetailsViewModel @Inject constructor(
    private val useCases: BitsoDetailsRepository,
) : BaseViewModel<DetailsContract.Event, DetailsContract.DetailState, DetailsContract.Effect>() {

    init {
        setEvent(DetailsContract.Event.OnInit)
    }

    override fun createInitialState(): DetailsContract.DetailState {
        return DetailsContract.DetailState(
            DetailsContract.DetailsBitsoApiState.Idle,
        )
    }

    /**
     * Handle each event
     */
    override fun handleEvent(event: DetailsContract.Event) {
        when (event) {
            is DetailsContract.Event.OnInit -> {}
            is DetailsContract.Event.OnNewClick -> {
                getBidsAsk(name = event.pair)
                getTrades(name = event.pair)
            }
        }
    }

    private fun getBidsAsk(name: String) {
        viewModelScope.launch {
            useCases.getBitsoBids(name)
                .catch { }
                .collect {
                    setState {
                        copy(getInfo = DetailsContract.DetailsBitsoApiState.Success(it))
                    }
                }
        }
    }

    private fun getTrades(name: String) {
        viewModelScope.launch {
            useCases.getBitsoTrades(name)
                .catch { }
                .take(15)
                .collect {
                    GetnewListTrades(it)
                }
        }
    }

    private suspend fun GetnewListTrades(openedPayloads: List<PayloadTrades>) {
        val returnlist = mutableListOf<PayloadTrades>()
        openedPayloads.forEach {
            returnlist.add(
                PayloadTrades(
                    amount = it.amount.take(10),
                    book = it.book,
                    maker_side = operationKind(it.maker_side),
                    price = it.price.take(10),
                ),
            )
        }
        setState {
            copy(getInfo = DetailsContract.DetailsBitsoApiState.SuccessTrades(returnlist))
        }
    }
}
