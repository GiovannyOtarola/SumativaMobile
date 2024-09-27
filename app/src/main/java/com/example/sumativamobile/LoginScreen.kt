package com.example.sumativamobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sumativamobile.UsuarioRepository

@Composable
fun LoginScreen(navController: NavController, onNavigateToRegister: () -> Unit) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf<String?>(null) }
    var isEmailEmpty by remember { mutableStateOf(false) }
    var isPasswordEmpty by remember { mutableStateOf(false) }
    val usuarioRepository = UsuarioRepository() // Crea una instancia del repositorio

    // degradado de fondo
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color.White, Color(0xFFB2DFDB)),
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )

    // Lógica para manejar el nivel de zoom y desplazamiento
    var zoomLevel by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoomChange, _ ->
                    // Actualiza el nivel de zoom
                    val newZoomLevel = (zoomLevel * zoomChange).coerceIn(1f, 3f) // Limita el zoom entre 1x y 3x
                    zoomLevel = newZoomLevel

                    // Actualiza el desplazamiento solo si el zoom es mayor que 1
                    if (zoomLevel > 1f) {
                        // Actualiza los offsets con el pan
                        offsetX += pan.x
                        offsetY += pan.y
                    } else {
                        // Restablece los offsets a 0 cuando el zoom es 1
                        offsetX = 0f
                        offsetY = 0f
                    }
                }
            }
            .graphicsLayer(
                scaleX = zoomLevel,
                scaleY = zoomLevel,
                translationX = offsetX,
                translationY = offsetY
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Login image",
                modifier = Modifier.size(200.dp)
            )

            Text(text = "Bienvenido", fontSize = 28.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Inicia Sesión con tu cuenta")

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = email, onValueChange = {
                email = it.replace(Regex("\\s"), "")
                isEmailEmpty = email.isBlank()
            }, shape = RoundedCornerShape(16.dp), label = { Text(text = "Dirección Email") })

            if (isEmailEmpty) {
                Text(text = "El campo de email no puede estar vacío", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = password, onValueChange = {
                password = it.replace(Regex("\\s"), "")
                isPasswordEmpty = password.isBlank()
            }, shape = RoundedCornerShape(16.dp), label = { Text(text = "Contraseña") }, visualTransformation = PasswordVisualTransformation())

            if (isPasswordEmpty) {
                Text(text = "El campo de contraseña no puede estar vacía", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    isEmailEmpty = email.isBlank()
                    isPasswordEmpty = password.isBlank()

                    if (!isEmailEmpty && !isPasswordEmpty) {
                        usuarioRepository.obtenerUsuarioPorEmailYContrasena(email, password) { user ->
                            if (user != null) {
                                loginError = null
                                navController.navigate("principal/$email")
                            } else {
                                loginError = "Credenciales incorrectas. Intenta nuevamente."
                            }
                        }
                    }
                }) {
                    Text(text = "Iniciar Sesión")
                }

                Button(onClick = { onNavigateToRegister() }) {
                    Text(text = "Registrate")
                }
            }

            loginError?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { navController.navigate("recuperar") }) {
                Text(text = "Olvidaste tu Contraseña?")
            }
        }

        // Widget de zoom
        Widgetzoom(
            onZoomChanged = { newZoomLevel ->
                zoomLevel = 1f + (newZoomLevel / 100)
            }
        )
    }
}