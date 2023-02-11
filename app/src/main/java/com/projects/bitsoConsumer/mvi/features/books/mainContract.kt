package com.projects.bitsoConsumer.mvi.features.books

import com.projects.bitsoConsumer.mvi.features.UiEffect
import com.projects.bitsoConsumer.mvi.features.UiEvent
import com.projects.bitsoConsumer.mvi.features.UiState
import com.projects.bitsoConsumer.models.DetailedPayload

class MainContract {

    // Events that user performed
    sealed class Event : UiEvent {
        object OnInit : Event()
        object OnNewClick : Event()
    }

    // Ui View States
    data class State(
        val getInfo: BitsoApiState,
    ) : UiState

    // View State that related to Random Number
    sealed class BitsoApiState {
        object Idle : BitsoApiState()
        data class Loading(val status: Boolean) : BitsoApiState()
        data class Success(val link: List<DetailedPayload>) : BitsoApiState()
        data class tick(val link: String) : BitsoApiState()
    }

    // Side effects
    sealed class Effect : UiEffect {

        object ShowToast : Effect()
    }
}
