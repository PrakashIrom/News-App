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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.model.ApiResponse

@Composable
fun HomeScreen(modifier: Modifier = Modifier.fillMaxSize()){
    val vModel = NewsViewModel()

    when(vModel.newsUIState){
        is NewsUIState.Loading -> Loading()
        is NewsUIState.Error -> Error()
        else -> {
            Success(modifier, vModel.newsLive)}
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
fun Success(modifier: Modifier = Modifier, newsApi: LiveData<ApiResponse>){
    Scaffold (topBar = {
        TopAppBar(title = { Text("News App")})
    }){innerPadding->
        NewsScreen(modifier.padding(innerPadding), newsApi.value?.articles)
    }
}