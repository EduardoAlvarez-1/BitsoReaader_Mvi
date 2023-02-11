package com.projects.bitsoConsumer.datasource
import com.projects.bitsoConsumer.models.Books
import com.projects.bitsoConsumer.models.bitsotickers.Tickers
import com.projects.bitsoConsumer.models.trading.Trades
import retrofit2.http.GET
import retrofit2.http.Query

interface BitsoDataSource {

    @GET("available_books")
    suspend fun getBooks(): Books

    }

interface BitsoDetailsSource{

    @GET("ticker/") // get especific
    suspend fun specificTicker(@Query("book") book: String): Tickers

    @GET("trades/")
    suspend fun specificTrade(@Query("book") book: String): Trades

}
