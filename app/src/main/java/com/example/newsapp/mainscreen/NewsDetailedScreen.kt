package com.example.newsapp.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.newsapp.model.ApiResponse
import com.example.newsapp.model.Article
import com.example.newsapp.navigation.NavApp


@Composable
fun NewsScreen(modifier:Modifier = Modifier, newsList: List<Article>?){

    val articles = newsList ?: emptyList() //this line is used as LazyColumn cannot passed null values as its items
    var details by remember {
        mutableStateOf(false)
    }


    LazyColumn(modifier){
        items(articles){ article->
            NewsList(modifier, article)
            Box(modifier.clickable {
              details=true
            }) {
                if (details) {
                    NavHost(modifier, article)
                } else {
                    NewsList(modifier, article)
                    Spacer(modifier = modifier.size(10.dp))
                }
            }
        }
    }
}

@Composable
fun NewsList(modifier:Modifier = Modifier, article: Article ){
        val title = article.title?: "Unknown title"
        Text(text = title)
        Spacer(modifier = modifier.size(20.dp))
    AsyncImage(
        model = article.urlToImage ?: "", // Use the image URL if not null, otherwise use an empty string
        contentDescription = "News Image",
        modifier = Modifier.fillMaxSize(),
        // You can adjust other parameters like contentScale, crossfade, etc. as needed
    )
}



@Composable
fun NavHost(modifier: Modifier=Modifier.fillMaxSize(), article: Article){
    NavApp(modifier,article)

}

@Composable
fun NewsDetailedScreen(modifier: Modifier = Modifier, article: Article){

    val desc = article.description?:"No Description"
    val cont = article.content?:"No Content"

            Text(text = desc)
            Spacer(modifier = modifier.size(10.dp))
            AsyncImage(model = article.urlToImage ?: "", contentDescription = "News Image")
            Spacer(modifier = modifier.size(10.dp))
            Text(text = cont)

}


