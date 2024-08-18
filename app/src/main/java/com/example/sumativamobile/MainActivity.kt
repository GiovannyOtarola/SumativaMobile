package com.example.sumativamobile

import RegistroScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sumativamobile.ui.theme.SumativaMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SumativaMobileTheme {
                // Configura el NavHostController para la navegación
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(
                            navController = navController,
                            onNavigateToRegister = {
                                navController.navigate("registro")
                            }
                        )
                    }
                    composable("registro") {
                        RegistroScreen(
                            onRegistrationComplete = {
                                // Navega de regreso a la pantalla de inicio de sesión
                                navController.popBackStack()
                            }
                        )
                    }
                    composable("principal") {
                        Principal()
                    }
                }
            }
        }
    }
}
