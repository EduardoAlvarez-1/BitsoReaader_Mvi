package com.projects.bitsoConsumer.repository

import com.projects.bitsoConsumer.models.bitsotickers.PayloadTickers
import com.projects.bitsoConsumer.models.trading.PayloadTrades
import kotlinx.coroutines.flow.Flow

interface BitsoDetailsRepository {

    suspend fun getBitsoBids(ticker: String): Flow<List<PayloadTickers>>
    suspend fun getBitsoTrades(ticker: String): Flow<List<PayloadTrades>>
    suspend fun insertTrades(trades: List<PayloadTrades>)
}
