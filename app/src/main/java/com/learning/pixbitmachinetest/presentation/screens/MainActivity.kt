package com.learning.pixbitmachinetest.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.learning.pixbitmachinetest.presentation.screens.MainViewModel
import com.learning.pixbitmachinetest.presentation.navigation.AppNavHost
import com.learning.pixbitmachinetest.presentation.navigation.Screen
import com.learning.pixbitmachinetest.presentation.theme.PixbitMachineTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoggedIn.value == null
            }
        }
        enableEdgeToEdge()
        setContent {
            PixbitMachineTestTheme {
                val isLoggedIn = viewModel.isLoggedIn.collectAsState().value
                if (isLoggedIn != null) {
                    AppNavHost(
                        startDestination = if (isLoggedIn) Screen.Home.route else Screen.Login.route
                    )
                }
            }
        }
    }
}