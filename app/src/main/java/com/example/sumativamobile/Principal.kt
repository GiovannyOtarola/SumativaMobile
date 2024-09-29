package com.example.sumativamobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
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

    // LOgica para manejar el nivel de zoom y desplazamiento
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
    )
    {
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

            Button(onClick = {
                navController.navigate("calcularImc/$loggedInEmail")
            },
                modifier =  Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(12.dp) // Esquinas redondeadas
            ) {
                Text(text = "Calcular IMC", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                navController.navigate("calcularproteinas")
            },
                modifier =  Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(12.dp) // Esquinas redondeadas
            ) {
                Text(text = "Calculadora de Proteínas", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                navController.navigate("Usuarios")
            },
                modifier =  Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Lista Usuarios", fontSize = 20.sp)
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