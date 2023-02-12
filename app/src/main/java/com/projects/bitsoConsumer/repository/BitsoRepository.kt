package com.projects.bitsoConsumer.repository

import com.projects.bitsoConsumer.models.BooksPayload
import kotlinx.coroutines.flow.Flow

interface BitsoRepository {

    suspend fun getTradeBooks(): Flow<List<BooksPayload>>
    suspend fun getBooks()
    suspend fun insertAskandBids(openedPayloadsCoin: List<BooksPayload>)
}
