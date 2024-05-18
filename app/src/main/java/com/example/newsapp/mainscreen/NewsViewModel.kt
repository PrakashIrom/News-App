package com.example.newsapp.mainscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.api.NewsApi
import com.example.newsapp.model.ApiResponse
import com.example.newsapp.model.Article
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface NewsUIState {
    object Loading: NewsUIState
    object Error: NewsUIState
    object Success: NewsUIState
}
class NewsViewModel: ViewModel(){

    var newsUIState: NewsUIState by mutableStateOf(NewsUIState.Loading) // by using 'by' the it delegates the implementation
    // of newsUIState to another object which is mutableStateOf ** cannot use '=' sign here **

    private val _newsLive = MutableLiveData<ApiResponse>()
    val newsLive: LiveData<ApiResponse> = _newsLive
    fun getNews(){
        viewModelScope.launch {
                try{
                    val response = NewsApi.retrofitService.getNews()
                    _newsLive.value = response// this one is not reinitializing, it is updating the value stored
                    newsUIState = NewsUIState.Success
                }
                catch (e: IOException) {
                    newsUIState=NewsUIState.Error
                } catch (e: HttpException) {
                    newsUIState=NewsUIState.Error
                }
        }
    }
}