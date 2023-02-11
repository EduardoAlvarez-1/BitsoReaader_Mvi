package com.projects.bitsoConsumer.datasource
import com.projects.bitsoConsumer.models.Books
import retrofit2.http.GET

interface BitsoDataSource {

    @GET("available_books")
    suspend fun getBooks(): Books
}
