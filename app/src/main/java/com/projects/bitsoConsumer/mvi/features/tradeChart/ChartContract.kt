package com.projects.bitsoConsumer.mvi.features.tradeChart

import com.projects.bitsoConsumer.models.trading.PayloadTrades
import com.projects.bitsoConsumer.mvi.features.UiEffect
import com.projects.bitsoConsumer.mvi.features.UiEvent
import com.projects.bitsoConsumer.mvi.features.UiState

class ChartContract {

    // Events that user performed
    sealed class Event : UiEvent {
        data class OnInit(val downloading: Boolean, val Update: Boolean) : Event()
        data class OnUpdate(val pair: String) : Event()
    }

    // Ui View States
    data class ChartState(
        val getInfo: ChartBitsoApiState,
    ) : UiState

    // View State that related to Random Number
    sealed class ChartBitsoApiState {
        object Idle : ChartBitsoApiState()
        data class Flags(val downloading: Boolean, val Update: Boolean) : ChartBitsoApiState()
        data class SuccessTrades(val link: MutableList<PayloadTrades>) : ChartBitsoApiState()
    }

    // Side effects
    sealed class Effect : UiEffect {

        object ShowToast : Effect()
    }
}
