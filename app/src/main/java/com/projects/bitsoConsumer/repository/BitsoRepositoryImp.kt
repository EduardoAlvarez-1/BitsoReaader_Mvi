package com.projects.bitsoConsumer.repository

import com.example.readbitso.ds.room.dao.entity.AskBids
import com.projects.bitsoConsumer.datasource.BitsoDataSource
import com.projects.bitsoConsumer.models.BooksPayload
import com.projects.bitsoConsumer.room.dao.AskBidsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BitsoRepositoryImp
@Inject
constructor(
    private val retro: BitsoDataSource,
    private val db: AskBidsDao,
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

    override suspend fun insertAskandBids(openedPayloadsCoin: List<BooksPayload>) {
        val ret = mutableListOf<AskBids>()
        openedPayloadsCoin.forEachIndexed { index, it ->
            ret.add(
                AskBids(
                    id = index,
                    Ask = it.maximum_price,
                    Bid = it.minimum_price,
                    Book = it.book,
                ),
            )
        }
        db.insertAll(ret)
    }
}
