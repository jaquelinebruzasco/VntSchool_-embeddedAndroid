package br.org.venturus.mentoriasoftex.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.org.venturus.mentoriasoftex.Routes

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {

        composable(Routes.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        
        composable(Routes.SendClicks.route) {
            SendScreenClicks(navController = navController)
        }

        composable(Routes.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(Routes.CreateAccountScreen.route) {
            CreateAccountScreen(navController = navController)
        }
        
        composable(Routes.WelcomeScreen.route) {
            WelcomeScreen(navController = navController)
        }
    }
}