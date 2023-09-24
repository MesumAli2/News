package com.example.news.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import okhttp3.OkHttpClient
import retrofit2.http.Query

private const val API_KEY = "76d0293e69c0f7e4731f2b659d98bb95"
private const val BASE_URL = "http://api.mediastack.com/v1/"

/**
 * A retrofit service to fetch a news list.
 */

private val requestInterceptor = Interceptor{ chain -> val url = chain.request().url().newBuilder().addQueryParameter("access_key", API_KEY ).build()
    val request = chain.request().newBuilder().url(url).build()
    return@Interceptor chain.proceed(request)
}

val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(requestInterceptor)
    .build()
//Moshi converter to convert json data into Kotlin Objects
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//Retrofit used to commuincate with Rest Api to fetch news data
private val retrofit  by lazy {
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}
//Interface for implementation of GET request to fetch news data
interface NewsByteService{
    @GET("news")
    suspend fun getNews(
        @Query("sources") sources: String, @Query("languages") language: String,@Query("access_key") accessKey : String = "76d0293e69c0f7e4731f2b659d98bb95",
        @Query("keywords") search: String, @Query("sort") sort: String,@Query("offset") offset: Int, @Query("limit") limit: Int
    ): NetworkNewsContainer
}

//Main entry point for network access. Call like `NewsByteNetwork.newByte.getNews()`

object NewsByteNetwork{
    // Configure retrofit to parse JSON and use coroutines
    val newsBytes = retrofit.create(NewsByteService::class.java)
}

