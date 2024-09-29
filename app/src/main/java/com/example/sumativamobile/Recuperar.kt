package com.example.sumativamobile

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Recuperar(context: Context) {
    var email by remember { mutableStateOf("") }
    val usuarioRepository = UsuarioRepository()

    // Degradado de fondo
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color.White, Color(0xFFB2DFDB)),
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.recuperar),
                contentDescription = "Recuperar image",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Recuperar Contraseña", fontSize = 28.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                shape = RoundedCornerShape(16.dp),
                label = { Text("Dirección Email") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                usuarioRepository.obtenerUsuarioPorEmail(email) { user ->
                    if (user != null) {
                        Toast.makeText(context, "Contraseña: ${user.password}", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "No se encontró un usuario con ese email.", Toast.LENGTH_LONG).show()
                    }
                }
            }) {
                Text(text = "Mostrar Contraseña")
            }
        }
    }
}