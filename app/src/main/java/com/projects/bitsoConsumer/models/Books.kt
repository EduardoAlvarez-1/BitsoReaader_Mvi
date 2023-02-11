package com.projects.bitsoConsumer.models

data class Books(
    val payload: List<BooksPayload>,
    val success: Boolean,
)
