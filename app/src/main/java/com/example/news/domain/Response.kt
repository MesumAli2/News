package com.example.news.domain

data class Response(
    var users: List<User>? = null,
    var exception: Exception? = null
)