package com.projects.bitsoConsumer.models

data class BooksPayload(
    val id: Int = 0,
    var book: String,
    val maximum_price: String,
    val minimum_price: String,
)
