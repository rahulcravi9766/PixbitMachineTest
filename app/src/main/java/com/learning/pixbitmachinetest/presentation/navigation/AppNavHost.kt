package com.learning.pixbitmachinetest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.learning.pixbitmachinetest.presentation.screens.addEmployee.AddEmployeeScreen
import com.learning.pixbitmachinetest.presentation.screens.home.HomeScreen
import com.learning.pixbitmachinetest.presentation.screens.profile.EmployeeProfileScreen
import com.learning.pixbitmachinetest.presentation.screens.signInSignUp.LoginScreen
import com.learning.pixbitmachinetest.presentation.screens.signInSignUp.RegistrationScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
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
                navController.navigate(Screen.AddEmployee.route)
            }, navigateToProfile = {
                navController.navigate("${Screen.EmployeeProfile.route}/$it")
            })
        }

        composable(Screen.AddEmployee.route) {
            AddEmployeeScreen(onBackPress = {
                navController.popBackStack()
            }, onSaveSuccess = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.AddEmployee.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            route = "${Screen.EmployeeProfile.route}/{employeeId}",
            arguments = listOf(navArgument("employeeId") { type = NavType.IntType })
        ) {
            EmployeeProfileScreen {
                navController.popBackStack()
            }
        }
    }
}