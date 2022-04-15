package com.example.news.repository

import android.telecom.Call
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.news.database.NewsDatabase
import com.example.news.database.asDomainModel
import com.example.news.domain.NewsByte
import com.example.news.network.NetworkNewsContainer
import com.example.news.network.NewsByteNetwork
import com.example.news.network.NewsByteNetworkJava
import com.example.news.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.security.auth.callback.Callback


/**
 * Repository for fetching newsByte news from the network and storing them on disk
 */
class NewsRepository (private val database: NewsDatabase){
    private val _newsList = MutableLiveData<NetworkNewsContainer>()
    val newsList : LiveData<NetworkNewsContainer> get() = _newsList


    var news: LiveData<List<NewsByte>> = Transformations.map(database.newsDao().getNews()){
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
   fun refreshNews(){
       /* withContext(Dispatchers.IO) {
            val newslist = NewsByteNetwork.newsBytes.getNews(
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
        }*/


            val newsRp = NewsByteNetworkJava.CreateService(NewsByteNetworkJava.TaskService::class.java)
            val call = newsRp.getNews(
                "60edb176f7611beaa6f74c76472203cd",
                "cnn,bbc,telegraf,nytimes,bloomberg,Yahoo News, independent,Post, Engadget, ABC",

                "en",
                "",
                "published_desc"

            )
                call.enqueue(object : retrofit2.Callback<NetworkNewsContainer> {
                override fun onResponse(
                    call: retrofit2.Call<NetworkNewsContainer>,
                    response: Response<NetworkNewsContainer>
                ) {
                    if (response.isSuccessful) {
                        _newsList.value = response.body()
                    }
                }

                override fun onFailure(call: retrofit2.Call<NetworkNewsContainer>, t: Throwable) {
                    Log.d("newrepo", "no response could be cached")

                }
            })


    }

    suspend fun searchNews(search: String) {
        withContext(Dispatchers.IO) {
            database.newsDao().clear()
            //  val newList = NewsByteNetwork.newsBytes.getNews(  "cnn,bbc", "en", search, "published_desc" )
            val newsReponse =
                NewsByteNetworkJava.CreateService(NewsByteNetworkJava.TaskService::class.java)
            val call = newsReponse.getNews(
                "60edb176f7611beaa6f74c76472203cd",
                "cnn,bbc,telegraf,nytimes,bloomberg,Yahoo News,Mail, independent,Post, Engadget, ABC, dailymail",
                "en",
                search,
                "published_desc"
            )
            call.enqueue(object : retrofit2.Callback<NetworkNewsContainer> {
                override fun onResponse(
                    call: retrofit2.Call<NetworkNewsContainer>,
                    response: Response<NetworkNewsContainer>
                ) {
                    _newsList.value = response.body()
                }
                override fun onFailure(call: retrofit2.Call<NetworkNewsContainer>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })


        }
    }


}