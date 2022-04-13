package com.example.news.domain

data class Pagination(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val total: Int
)