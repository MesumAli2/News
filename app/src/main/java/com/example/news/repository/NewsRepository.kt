package com.example.news.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.news.database.NewsDatabase
import com.example.news.database.asDomainModel
import com.example.news.domain.NewsByte
import com.example.news.network.NewsByteNetwork
import com.example.news.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * Repository for fetching newsByte news from the network and storing them on disk
 */
class NewsRepository (private val database: NewsDatabase){
    var news: LiveData<List<NewsByte>> = Transformations.map(database.newsDao().getVideos()){
        it.asDomainModel()
    }
    /**
     * Refresh the news stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     */
    suspend fun refreshNews() {
        withContext(Dispatchers.IO) {
            val newslist = NewsByteNetwork.newsBytes.getNews(
                "76d0293e69c0f7e4731f2b659d98bb95",
                "cnn,bbc",
                "en",
                "",
                "published_desc"
            )
            //if there's response from the network delete the previous save dataset in DB
            if(!newslist.news.isNullOrEmpty()) {
                database.newsDao().clear()
            }
            database.newsDao().insertAll(newslist.asDatabaseModel())
        }
    }
    suspend fun searchNews(search: String) {
        withContext(Dispatchers.IO) {
            database.newsDao().clear()
           val newList = NewsByteNetwork.newsBytes.getNews( "76d0293e69c0f7e4731f2b659d98bb95", "cnn,bbc", "en", search, "published_desc" )
           database.newsDao().insertAll(newList.asDatabaseModel())


            database.newsDao()



        }


    }




}