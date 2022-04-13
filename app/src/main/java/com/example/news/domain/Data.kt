package com.example.news.domain

data class Data(
    val author: Any,
    val category: String,
    val country: String,
    val description: String,
    val image: String,
    val language: String,
    val published_at: String,
    val source: String,
    val title: String,
    val url: String
)