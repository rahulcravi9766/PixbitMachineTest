package com.learning.pixbitmachinetest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.learning.pixbitmachinetest.presentation.screens.home.HomeScreen
import com.learning.pixbitmachinetest.presentation.screens.signInSignUp.LoginScreen
import com.learning.pixbitmachinetest.presentation.screens.signInSignUp.RegistrationScreen

sealed class Screen(val route: String) {

    data object Login : Screen("login")
    data object Registration : Screen("registration")
    data object Home : Screen("home")
}


@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Login.route) {
            LoginScreen(onRegister = {
                navController.navigate(Screen.Registration.route)
            }, onLoginSuccess = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(Screen.Registration.route) {
            RegistrationScreen(onBackClick = {
                navController.popBackStack()
            }, onRegistrationSuccess = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(Screen.Home.route) {
            HomeScreen(navigateToAddEmployee = {

            }, navigateToProfile = {

            })
        }
    }
}
