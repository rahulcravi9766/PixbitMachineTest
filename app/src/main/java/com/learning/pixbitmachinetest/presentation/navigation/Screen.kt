package com.learning.pixbitmachinetest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.learning.pixbitmachinetest.presentation.screens.signInSignUp.LoginScreen
import com.learning.pixbitmachinetest.presentation.screens.signInSignUp.RegistrationScreen

sealed class Screen(val route: String) {

    data object Login: Screen("login")
    data object Registration: Screen("registration")
}


@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Login.route) {
            LoginScreen{
                navController.navigate(Screen.Registration.route)
            }
        }

        composable(Screen.Registration.route) {
            RegistrationScreen {
                navController.popBackStack()
            }
        }
    }
}