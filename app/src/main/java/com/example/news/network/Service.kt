package com.example.news.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

/**
 * A retrofit service to fetch a devbyte playlist.
 */
interface NewsByteService{
    @GET("news")
    suspend fun getNews(@Query("access_key")accesKey: String, @Query("sources") sources: String, @Query("languages") language: String, @Query("keywords") search: String, @Query("sort") sort: String ): NetworkNewsContainer
}
/**
 * Main entry point for network access. Call like `NewsByteNetwork.newByte.getNews()`
 */
object NewsByteNetwork{
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit  = Retrofit.Builder()
        .baseUrl("http://api.mediastack.com/v1/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val newsBytes = retrofit.create(NewsByteService::class.java)
}

