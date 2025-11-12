package com.learning.pixbitmachinetest.presentation.navigation

sealed class Screen(val route: String) {

    data object Login : Screen("login")
    data object Registration : Screen("registration")
    data object Home : Screen("home")
    data object AddEmployee : Screen("add_employee")
    data object EmployeeProfile : Screen("employee_profile")
}
