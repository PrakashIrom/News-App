package com.example.newsapp.model

import kotlinx.serialization.Serializable

data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String
)

data class Source(
    val id: String?,
    val name: String
)

data class ApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
