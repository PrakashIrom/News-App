package com.example.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.mainscreen.HomeScreen
import com.example.newsapp.mainscreen.NewsDetailedScreen
import com.example.newsapp.mainscreen.NewsViewModel
import com.example.newsapp.model.Article

@Composable
fun NavApp(modifier: Modifier,article: Article){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "details" ){

        composable("details"){
            NewsDetailedScreen(modifier, article)
        }
    }
    
}