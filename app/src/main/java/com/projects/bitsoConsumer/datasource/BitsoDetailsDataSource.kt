package com.projects.bitsoConsumer.datasource

import com.projects.bitsoConsumer.models.bitsotickers.Tickers
import com.projects.bitsoConsumer.models.trading.Trades
import retrofit2.http.GET
import retrofit2.http.Query

interface BitsoDetailsDataSource {

    @GET("ticker/") // get especific
    suspend fun specificTicker(@Query("book") book: String): Tickers

    @GET("trades/")
    suspend fun specificTrade(@Query("book") book: String): Trades
}
