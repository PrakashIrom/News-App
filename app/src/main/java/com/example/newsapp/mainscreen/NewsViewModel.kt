package com.example.newsapp.mainscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.api.NewsApi
import com.example.newsapp.api.NewsApiService
import com.example.newsapp.model.ApiResponse
import com.example.newsapp.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface NewsUIState {
    object Loading: NewsUIState
    object Error: NewsUIState
    data class Success(val articles: List<Article>): NewsUIState
}
class NewsViewModel: ViewModel(){

    private val _newsUIState = MutableStateFlow<NewsUIState>(NewsUIState.Loading)
    val newsUIState: StateFlow<NewsUIState> = _newsUIState

    init {
        getNews()
    }
    private fun getNews(){
        viewModelScope.launch {
            _newsUIState.value = NewsUIState.Loading
            _newsUIState.value = try{ // this one is not reinitializing, it is updating the value stored
                    val response = NewsApi.retrofitService.getNews()
                     NewsUIState.Success(response.articles)
                }
                catch (e: IOException) {
                    NewsUIState.Error
                } catch (e: HttpException) {
                    NewsUIState.Error
                }
        }
    }
}