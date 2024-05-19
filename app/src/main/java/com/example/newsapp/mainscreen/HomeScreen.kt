package com.example.newsapp.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.R
import com.example.newsapp.model.ApiResponse
import com.example.newsapp.model.Article

@Composable
fun HomeScreen(modifier: Modifier = Modifier.fillMaxSize(), viewModel: NewsViewModel = viewModel()) {

    val newsUIState by viewModel.newsUIState.collectAsState()

    when (val current = newsUIState)// we can directly use newsUIState to access Success's property
    {
        is NewsUIState.Loading -> Loading()
        is NewsUIState.Error -> Error()
        is NewsUIState.Success -> {
            Success(modifier, current.article)
        }
    }
}
@Composable
fun Loading(modifier: Modifier = Modifier){
        Image(painter = painterResource(id = R.drawable.loading_img),
            contentDescription = "Loading Icon",
            modifier = modifier.size(200.dp)
            )
}

@Composable
fun Error(modifier: Modifier  = Modifier){
    Image(painter= painterResource(id = R.drawable.ic_connection_error),
        contentDescription = "Error Icon",
        modifier = modifier.size(200.dp)
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Success(modifier: Modifier = Modifier, article: List<Article>){
    Scaffold (topBar = {
        TopAppBar(title = { Text("News App")})
    }){innerPadding->
        NewsScreen(modifier.padding(innerPadding), article)
    }
}