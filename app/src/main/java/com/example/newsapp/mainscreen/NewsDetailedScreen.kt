package com.example.newsapp.mainscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.newsapp.model.ApiResponse
import com.example.newsapp.model.Article
import com.example.newsapp.navigation.NavApp
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*
@Composable
fun NewsScreen(modifier:Modifier = Modifier, newsList: List<Article>?,navController: NavController){


    val articles = newsList ?: emptyList() //this line is used as LazyColumn cannot passed null values as its items

    LazyColumn(){
        items(articles){ article->
                    NewsList(modifier, article,
                        onItemClick ={selectedArticle ->
                            val encodedUrl = URLEncoder.encode(selectedArticle.url, StandardCharsets.UTF_8.toString())
                            val route = "details/$encodedUrl"
                            navController.navigate(route)
                        })
        }
    }
}

@Composable
fun NewsList(modifier:Modifier = Modifier, article: Article,  onItemClick:(Article)->Unit){
        val title = article.title?: "Unknown title"

    Card (modifier.clickable { onItemClick(article) }){
        Text(text = title)
        Spacer(modifier = modifier.size(20.dp))
        AsyncImage(

            model = article.urlToImage ?: "", // Use the image URL if not null, otherwise use an empty string
            contentDescription = "News Image",
            modifier = Modifier.fillMaxSize(),
            // You can adjust other parameters like contentScale, cross-fade, etc. as needed
        )
    }
    Spacer(modifier = modifier.size(20.dp))
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun NewsDetailedScreen(modifier: Modifier = Modifier, article: Article) {
    val desc = article.description ?: "No Description"
    val cont = article.content ?: "No Content"
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val date = article.publishedAt?.let { formatter.parse(it) }
    val formattedDate = date?.let {
        SimpleDateFormat("MMM dd, yyyy, HH:mm", Locale.getDefault()).format(it)
    } ?: "Unknown Date"

    LazyColumn(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        item {
            article.urlToImage?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "News Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        item {
            Text(
                text = article.title ?: "Unknown Title",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            Text(
                text = "By ${article.author ?: "Unknown Author"}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            Text(
                text = "Source: ${article.source.name}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(
                text = cont,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            TextButton(onClick = { /* Handle click to open the URL */ }) {
                Text(text = "Read more", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}


