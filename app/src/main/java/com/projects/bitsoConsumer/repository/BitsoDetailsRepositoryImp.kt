package com.projects.bitsoConsumer.repository

import com.example.readbitso.ds.room.dao.entity.OperationsTrades
import com.projects.bitsoConsumer.datasource.BitsoDetailsDataSource
import com.projects.bitsoConsumer.models.bitsotickers.PayloadTickers
import com.projects.bitsoConsumer.models.trading.PayloadTrades
import com.projects.bitsoConsumer.room.dao.AskBidsDao
import com.projects.bitsoConsumer.room.dao.TradesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BitsoDetailsRepositoryImp
@Inject constructor(
    private val retro: BitsoDetailsDataSource,
    private val dbT: TradesDao,
    private val db: AskBidsDao,
) :
    BitsoDetailsRepository {
    override suspend fun getBitsoBids(ticker: String): Flow<List<PayloadTickers>> =
        flow { emit(listOf(retro.specificTicker(ticker).payload)) }

    override suspend fun getBitsoTrades(ticker: String): Flow<List<PayloadTrades>> =
        flow { emit(retro.specificTrade(ticker).payload) }

    override suspend fun insertTrades(trades: List<PayloadTrades>) {
        val ret = mutableListOf<OperationsTrades>()
        trades.forEachIndexed { index, it ->
            ret.add(
                OperationsTrades(
                    id = index,
                    pair = it.book,
                    Amount = it.amount,
                    Type = it.maker_side,
                    Price = it.price,
                ),
            )
        }
        dbT.insertAll(ret)
    }
}
