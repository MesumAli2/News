package com.example.news.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.news.domain.NewsByte


/**
 * Database entities go in this file. These are responsible for reading and writing from the
 * database.
 */
@Entity
data class DatabaseNews constructor(
    @PrimaryKey()
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name ="author")
    val author: String?,
    @ColumnInfo(name ="source")
    val source: String,
    @ColumnInfo(name ="images")
    val images: String?,
    @ColumnInfo(name= "url")
    val url: String?,
    @ColumnInfo(name ="category")
    val category: String,
    @ColumnInfo(name ="language")
    val language: String,
    @ColumnInfo(name ="country")
    val country: String,
    @ColumnInfo(name ="publishedAt")
    val publishedAt: String

)

/**
 * Map DatabaseVideos to domain entities
 */

fun List<DatabaseNews>.asDomainModel(): List<NewsByte>{
    return map{
        NewsByte(
            url = it.url,
            author = it.author,
            title = it.title,
            description = it.description,
            source = it.source,
            image = it.images,
            category = it.category,
            language = it.language,
            country = it.country,
            publishedAt = it.publishedAt
        )
    }
}
