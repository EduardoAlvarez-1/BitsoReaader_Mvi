package com.projects.bitsoConsumer.repository

import com.projects.bitsoConsumer.datasource.BitsoDataSource
import com.projects.bitsoConsumer.datasource.BitsoDetailsSource
import com.projects.bitsoConsumer.models.BooksPayload
import com.projects.bitsoConsumer.models.bitsotickers.PayloadTickers
import com.projects.bitsoConsumer.models.trading.PayloadTrades
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BitsoRepositoryImp
@Inject
constructor(
    private val retro: BitsoDataSource,
) : BitsoRepository {
    override suspend fun getTradeBooks(): Flow<List<BooksPayload>> {
        return flow {
            emit(retro.getBooks().payload)
        }
    }

    override suspend fun getBooks() {
        try {
            println(retro.getBooks())
        } catch (e: Exception) {
            println(e)
        }
    }
}

class BitsoDetailImp
@Inject constructor(private val retro: BitsoDetailsSource) :
    BitsoDetailsRepository {
    override suspend fun getBitsoBids(ticker: String): Flow<List<PayloadTickers>> =
        flow { emit(listOf(retro.specificTicker(ticker).payload)) }

    override suspend fun getBitsoTrades(ticker: String): Flow<List<PayloadTrades>> =
        flow { emit(retro.specificTrade(ticker).payload) }
}
