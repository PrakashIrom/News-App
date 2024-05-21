package com.example.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.mainscreen.HomeScreen
import com.example.newsapp.mainscreen.NewsDetailedScreen
import com.example.newsapp.mainscreen.Success
import com.example.newsapp.model.Article
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


@Composable
fun NavApp(modifier: Modifier,articles: List<Article>,navController: NavHostController){

    NavHost(navController = navController, startDestination = "home" ){

        composable("home"){
            Success(modifier, articles,navController)
        }

        composable( route = "details/{articleURL}",
            arguments = listOf(navArgument("articleURL") { type = NavType.StringType })){
                backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("articleURL")
            val decodedUrl = encodedUrl?.let { URLDecoder.decode(it, StandardCharsets.UTF_8.toString()) }
            val selectedArticle = articles.find { it.url == decodedUrl }
            selectedArticle?.let { article ->
                NewsDetailedScreen(article = article, navigateUp = {navController.navigateUp()})
            }
        }
    }
}


