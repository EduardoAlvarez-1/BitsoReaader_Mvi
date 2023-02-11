package com.projects.bitsoConsumer.models

data class DetailedPayload(
    var payload: BooksPayload,
    var shortname: String,
    var name: String = "",
    var icon: Int,
    var pair: String,
)
