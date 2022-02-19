package com.example.news.network

import com.example.news.database.DatabaseNews
import com.example.news.domain.NewsByte
import com.squareup.moshi.Json

/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. You should convert these to domain objects before
 * using them.
 *
 * @see domain package for
 */

/**
 * NewsHolder holds a list of news.
 *
 * This is to parse first level of our network result which looks like
 *
 * {
 *   "news": []
 * }
 */
data class NetworkNewsContainer(@Json(name = "data")val news: List<NetworkNews>)

/**
 * News represent a newsByte that can be viewed.
 */
data class NetworkNews(
    @Json(name = "author")
    val author: String?,
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "url")
    val url: String?,
    @Json(name = "source")
    val source: String,
    @Json(name = "image")
    val image: String?,
    @Json(name = "category")
    val category: String,
    @Json(name = "language")
    val language: String,
    @Json(name = "country")
    val country: String,
    @Json(name = "published_at")
    val publishedAt: String
)
/**
 * Convert Network results to domain objects
 */

fun NetworkNewsContainer.asDomainModel(): List<NewsByte>{
    return news.map {
        NewsByte(
            author = it.author,
            title = it.title,
            description = it.description,
            url = it.url,
            source = it.source,
            image = it.image,
            category = it.category,
            language = it.language,
            country = it.country,
            publishedAt = it.publishedAt
        )
    }
}
/**
 * Convert Network results to database objects
 */

fun NetworkNewsContainer.asDatabaseModel(): List<DatabaseNews>{
    return news.map {
        DatabaseNews(
            url = it.url,
            author = it.author,
            title = it.title,
            description = it.description,
            source = it.source,
            images = it.image,
            category = it.category,
            language = it.language,
            country = it.country,
            publishedAt = it.publishedAt
        )
    }
}