package com.example.sumativamobile

import RegistroScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sumativamobile.ui.theme.SumativaMobileTheme
import com.example.sumativamobile.UsuarioRepository


class MainActivity : ComponentActivity() {
    private lateinit var listaUsuarios: listaUsuarios
    private lateinit var usuarioRepository: UsuarioRepository // Agregar repositorio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listaUsuarios = listaUsuarios(this)
        usuarioRepository = UsuarioRepository() // Inicializar el repositorio

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
                            },

                        )
                    }
                    composable("recuperar") {
                        Recuperar(context = this@MainActivity)
                    }
                    composable("principal/{loggedInEmail}") { backStackEntry ->
                        val loggedInEmail = backStackEntry.arguments?.getString("loggedInEmail") ?: ""
                        Principal(
                            listaUsuarios = listaUsuarios,
                            loggedInEmail = loggedInEmail,
                            navController = navController
                        )
                    }
                    composable("calcularimc/{loggedInEmail}") { backStackEntry ->
                        val loggedInEmail = backStackEntry.arguments?.getString("loggedInEmail") ?: ""
                        CalcularImc(
                            loggedInEmail = loggedInEmail
                        )
                    }
                    composable("usuarios") {
                        Usuarios(
                            usuarioRepository = usuarioRepository,
                            navController = navController // Pasar también el NavController
                        )
                    }
                    // Agregamos la nueva ruta para la vista CalcularProteinas
                    composable("calcularproteinas") {
                        CalcularProteinas()
                    }

                    composable("EditarUsuarioScreen/{userId}") { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId") ?: ""
                        EditarUsuarioScreen(userId, usuarioRepository, navController)
                    }

                }
            }
        }
    }
}
