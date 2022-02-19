package com.example.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.news.database.NewsDatabase
import com.example.news.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(application: Application): ViewModel() {


    /**
     * The data source this ViewModel will fetch results from.
     */
    private val newsRepository = NewsRepository(NewsDatabase.getDatabase(application))

    /**
     * A playlist of videos displayed on the screen.
     */
    val playlist = newsRepository.news

    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)



    var searchResult: String = ""


    /**
     * Flag to display the error message. Views should use this to get access
     * to the data.
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


    init {
        refreshDataFromRepository()
    }

    fun serachNews(search: String) {
        viewModelScope.launch {
            try {
                newsRepository.searchNews(search)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
                //_news.value = ApiClient.apiService.getNews("76d0293e69c0f7e4731f2b659d98bb95","cnn,bbc" ,"en", "$searchResult","published_desc")
            } catch (e: Exception) {
                if (playlist.value.isNullOrEmpty()){
                    _eventNetworkError.value = true
                }
                Log.d("fail", "no working man : $e back to you mesum")

            }
        }
    }

    fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                newsRepository.refreshNews()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (e: Exception) {
                if (playlist.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }


    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }


}