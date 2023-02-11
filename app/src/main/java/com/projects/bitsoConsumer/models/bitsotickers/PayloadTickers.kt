package com.projects.bitsoConsumer.models.bitsotickers

data class PayloadTickers(
    val id: Int = 0,
    val ask: String?,
    val bid: String?,
    val book: String?,
)
