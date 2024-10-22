package com.technologia.ticketbookingui.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.technologia.ticketbookingui.Presentation.DetailScreen
import com.technologia.ticketbookingui.Presentation.HomeScreen
import com.technologia.ticketbookingui.models.nowPlaying

object AppRoute {

    @Composable
    fun GenerateRoute(navController:NavHostController,isMyMode:Boolean,onModeToggle:()->Unit) {
        NavHost(
            navController =navController ,
            startDestination = RouteName.HOME,)
        {
            composable(RouteName.HOME){
                HomeScreen(
                    navHostController = navController,
                    isMyMode = isMyMode,
                    onModeToggle = onModeToggle
                )
            }

            composable("${RouteName.DETAILS}/{id}"){backStackEntry->
                val id = backStackEntry.arguments?.getString("id")
                val movie = nowPlaying.first{
                    it.id == id
                }
                DetailScreen(
                    navController = navController,
                    movieModel = movie)
            }
        }
    }
}