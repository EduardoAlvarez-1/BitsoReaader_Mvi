package com.projects.bitsoConsumer.models.trading

data class PayloadTrades(
    val amount: String,
    val book: String,
    val maker_side: String,
    val price: String
)

data class PayloadTradesGraph(
    val index: Int,
    val price: Double
)



