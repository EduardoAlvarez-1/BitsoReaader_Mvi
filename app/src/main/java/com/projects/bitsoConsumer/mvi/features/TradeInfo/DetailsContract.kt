package com.projects.bitsoConsumer.mvi.features.TradeInfo

import com.projects.bitsoConsumer.models.bitsotickers.PayloadTickers
import com.projects.bitsoConsumer.models.trading.PayloadTrades
import com.projects.bitsoConsumer.mvi.features.UiEffect
import com.projects.bitsoConsumer.mvi.features.UiEvent
import com.projects.bitsoConsumer.mvi.features.UiState

class DetailContract {

    // Events that user performed
    sealed class Event : UiEvent {
        object OnInit : Event()
        data class OnNewClick(val pair: String) : Event()
    }

    // Ui View States
    data class DetailState(
        val getInfo: DetailsBitsoApiState,
    ) : UiState

    // View State that related to Random Number
    sealed class DetailsBitsoApiState {
        object Idle : DetailsBitsoApiState()
        data class Loading(val status: Boolean) : DetailsBitsoApiState()
        data class Success(val link: List<PayloadTickers>) : DetailsBitsoApiState()
        data class SuccessTrades(val link: MutableList<PayloadTrades>) : DetailsBitsoApiState() }

    // Side effects
    sealed class Effect : UiEffect {

        object ShowToast : Effect()
    }
}
