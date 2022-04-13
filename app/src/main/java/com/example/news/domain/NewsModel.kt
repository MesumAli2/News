package com.example.news.domain

data class NewsModel(
    val `data`: List<Data>,
    val pagination: Pagination
)