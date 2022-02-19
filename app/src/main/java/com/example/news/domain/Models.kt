package com.example.news.domain


/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 *
 * @see database for objects that are mapped to the database
 * @see network for objects that parse or prepare network calls
 */

/**
 * News represent a newsbyte that can be played.hello how rt
 *
 */
data class NewsByte(
    val author: String?,
    val title: String,
    val description: String,
    val url: String?,
    val source: String,
    val image: String?,
    val category: String,
    val language: String,
    val country: String,
    val publishedAt: String
    )


