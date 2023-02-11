package com.projects.bitsoConsumer.repository

import com.projects.bitsoConsumer.models.BooksPayload
import com.projects.bitsoConsumer.models.bitsotickers.PayloadTickers
import com.projects.bitsoConsumer.models.trading.PayloadTrades
import kotlinx.coroutines.flow.Flow

interface BitsoRepository {

    suspend fun getTradeBooks(): Flow<List<BooksPayload>>
    suspend fun getBooks()
}

interface BitsoDetailsRepository {

    suspend fun getBitsoBids(ticker: String): Flow<List<PayloadTickers>>
    suspend fun getBitsoTrades(ticker: String): Flow<List<PayloadTrades>>
}
