package com.projects.bitsoConsumer.repository

import com.projects.bitsoConsumer.datasource.BitsoDataSource
import com.projects.bitsoConsumer.models.BooksPayload
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

