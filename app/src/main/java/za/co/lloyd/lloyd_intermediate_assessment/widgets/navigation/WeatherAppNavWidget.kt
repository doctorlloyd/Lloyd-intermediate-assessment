package za.co.lloyd.lloyd_intermediate_assessment.widgets.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import za.co.lloyd.lloyd_intermediate_assessment.screens.home.HomeScreen

@Composable
fun ToDoAppNavWidget() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ToDoAppNavScreens.ToDoScreen.name) {
        composable(ToDoAppNavScreens.ToDoScreen.name) {
            HomeScreen(navController = navController)
        }
        composable(ToDoAppNavScreens.CompletedScreen.name) {
//            SearchScreen(navController = navController)
        }
    }
}