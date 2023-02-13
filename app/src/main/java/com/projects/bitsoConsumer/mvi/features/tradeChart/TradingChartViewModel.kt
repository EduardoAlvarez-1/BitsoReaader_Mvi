package com.projects.bitsoConsumer.mvi.features.tradeChart

import androidx.lifecycle.viewModelScope
import com.projects.bitsoConsumer.models.trading.PayloadTrades
import com.projects.bitsoConsumer.mvi.features.BaseViewModel
import com.projects.bitsoConsumer.repository.BitsoDetailsRepository
import com.projects.bitsoConsumer.support.operationKind
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TradingChartViewModel @Inject constructor(
    private val useCases: BitsoDetailsRepository,
) : BaseViewModel<ChartContract.Event, ChartContract.ChartState, ChartContract.Effect>() {

    init {
        setEvent(ChartContract.Event.OnInit(downloading = true, Update = false))
    }

    override fun createInitialState(): ChartContract.ChartState {
        return ChartContract.ChartState(
            ChartContract.ChartBitsoApiState.Idle,
        )
    }

    /**
     * Handle each event
     */
    override fun handleEvent(event: ChartContract.Event) {
        when (event) {
            is ChartContract.Event.OnInit -> {
                setFlags(upd = event.Update, dow = event.downloading) }
            is ChartContract.Event.OnUpdate -> {
                getTrades(name = event.pair)
            }
        }
    }

    private fun setFlags(upd: Boolean, dow: Boolean) {
        viewModelScope.launch {
            setState {
                copy(getInfo = ChartContract.ChartBitsoApiState.Flags(Update = upd, downloading = dow))
            }
        }
    }
    private fun getTrades(name: String) {
        viewModelScope.launch {
            useCases.getBitsoTrades(name)
                .catch { }
                .take(15)
                .collect {
                    GetNewListTrades(it).apply {
                        useCases.insertTrades(this)
                    }
                }
        }
    }

    private fun GetNewListTrades(openedPayloads: List<PayloadTrades>): List<PayloadTrades> {
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
            copy(getInfo = ChartContract.ChartBitsoApiState.SuccessTrades(returnlist))
        }
        return returnlist
    }
}
