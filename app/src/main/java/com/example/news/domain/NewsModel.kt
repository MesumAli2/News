package com.example.news.domain

import com.example.news.database.DatabaseNews

data class NewsModel(
    val `data`: List<Data>,
    val pagination: Pagination
)

