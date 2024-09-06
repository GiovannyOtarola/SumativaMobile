package com.example.sumativamobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController


@Composable
fun Principal(navController: NavController, listaUsuarios: listaUsuarios, loggedInEmail: String) {
    val users = listaUsuarios.getUserList()

    // degradado de fondo
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color.White, Color(0xFFB2DFDB)), // Degradado blanco a verde claro
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(16.dp)
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Card para mostrar el correo del usuario logueado
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                Image(painter = painterResource(id = R.drawable.avatar), contentDescription = "Login Imagen",
                    modifier = Modifier.size(80.dp))
                Text(text = "Hola, $loggedInEmail",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "¿Que quieres hacer Hoy?", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))


        // Botón para navegar a la pantalla CalcularImp
        Button(onClick = {
            // Navegación a la pantalla CalcularImp
            navController.navigate("calcularImc/$loggedInEmail")
        },
            modifier =  Modifier
                .fillMaxWidth()
                .height(60.dp), // Aumenta la altura del botón
            shape = RoundedCornerShape(12.dp) // Esquinas redondeadas
            ) {
            Text(text = "Calcular IMC", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para navegar a la pantalla Usuarios
        Button(onClick = {
            // Navegación a la pantalla CalcularImp
            navController.navigate("calcularproteinas")
        },
            modifier =  Modifier
                .fillMaxWidth()
                .height(60.dp), // Aumenta la altura del botón
            shape = RoundedCornerShape(12.dp) // Esquinas redondeadas
        ) {
            Text(text = "Calculadora de Proteínas", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para navegar a la pantalla Usuarios
        Button(onClick = {
            // Navegación a la pantalla CalcularImp
            navController.navigate("Usuarios")
        },
            modifier =  Modifier
                .fillMaxWidth()
                .height(60.dp), // Aumenta la altura del botón
            shape = RoundedCornerShape(12.dp) // Esquinas redondeadas
            ) {
            Text(text = "Lista Usuarios", fontSize = 20.sp)
        }
    }
}