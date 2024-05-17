package com.example.newsapp.api

import com.example.newsapp.model.ApiResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query


private const val NEWS_BASE_URL = "https://newsapi.org/v2/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(NEWS_BASE_URL).build()


interface NewsApiService {
    @GET("top-headlines")
    suspend fun getNews(@Query("country") country: String = "us",
                        @Query("category") category: String = "business",
                        @Query("apiKey") apiKey: String = "4f88f54e7af3443da3baf0153c3d72cf"): ApiResponse
}


//Note: Remember "lazy initialization" is when object creation is purposely delayed, until you actually need that object,
// to avoid unnecessary computation or use of other computing resources. Kotlin has first-class support for lazy instantiation.

object NewsApi {
    val retrofitService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)//::class.java syntax is used to obtain the Java
        // Class object corresponding to a Kotlin class or type.
    }
}
