package com.projects.bitsoConsumer.repository

import com.projects.bitsoConsumer.datasource.BitsoDetailsDataSource
import com.projects.bitsoConsumer.models.bitsotickers.PayloadTickers
import com.projects.bitsoConsumer.models.trading.PayloadTrades
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BitsoDetailsImp
@Inject constructor(private val retro: BitsoDetailsDataSource) :
    BitsoDetailsRepository {
    override suspend fun getBitsoBids(ticker: String): Flow<List<PayloadTickers>> =
        flow { emit(listOf(retro.specificTicker(ticker).payload)) }

    override suspend fun getBitsoTrades(ticker: String): Flow<List<PayloadTrades>> =
        flow { emit(retro.specificTrade(ticker).payload) }
}
