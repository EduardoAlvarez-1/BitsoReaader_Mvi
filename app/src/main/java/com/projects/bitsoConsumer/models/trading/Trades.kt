package com.projects.bitsoConsumer.models.trading

data class Trades(
    val payload: List<PayloadTrades>,
    val success: Boolean,
)
