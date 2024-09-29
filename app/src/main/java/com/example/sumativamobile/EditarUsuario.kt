package com.example.sumativamobile


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController

@Composable
fun EditarUsuarioScreen(usuarioId: String, usuarioRepository: UsuarioRepository, navController: NavController) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var userNotFound by remember { mutableStateOf(false) }

    // Carga los datos del usuario al inicio
    LaunchedEffect(usuarioId) {
        usuarioRepository.obtenerUsuario(usuarioId) { usuario ->
            if (usuario != null) {
                email = usuario.email ?: ""
                password = usuario.password ?: ""
                isLoading = false
            } else {
                userNotFound = true
                isLoading = false
            }
        }
    }

    if (isLoading) {
        CircularProgressIndicator() // Mostrar un indicador de carga
    } else if (userNotFound) {
        Text(text = "Usuario no encontrado", color = Color.Red) // Mensaje de usuario no encontrado
    } else {

        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },

            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },

            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                val usuarioActualizado = Usuario(usuarioId, email, password)
                usuarioRepository.actualizarUsuario(usuarioActualizado) { success ->
                    if (success) {
                        Toast.makeText(context, "Usuario actualizado", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "Error al actualizar el usuario", Toast.LENGTH_SHORT).show()
                    }
                }
            }) {
                Text(text = "Guardar Cambios")
            }
        }
    }
}