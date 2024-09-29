package com.example.sumativamobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CalcularProteinas( ) {
    var peso by remember { mutableStateOf("") }
    var actividad by remember { mutableStateOf("") }
    var proteinas by remember { mutableStateOf(0.0) }
    var errorMessage by remember { mutableStateOf("") }

    fun calcularProteinas(peso: Double, nivelActividad: String): Double {
        return when (nivelActividad) {
            "Bajo" -> peso * 0.8
            "Moderado" -> peso * 1.0
            "Alto" -> peso * 1.2
            else -> 0.0
        }
    }

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
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.proteina), contentDescription = "Proteina image",
                modifier = Modifier.size(150.dp))

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = peso,
                onValueChange = { peso = it },
                label = { Text("Ingresa tu peso (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = actividad,
                onValueChange = { actividad = it },
                label = { Text("Nivel de actividad (Bajo, Moderado, Alto)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    try {
                        val pesoDouble = peso.toDoubleOrNull()
                        if (pesoDouble != null && pesoDouble > 0) {
                            proteinas = calcularProteinas(pesoDouble, actividad)
                            errorMessage = "" // Limpiar el mensaje de error si el cálculo es exitoso
                        } else {
                            errorMessage = "Por favor, ingresa un peso válido."
                        }
                    } catch (e: Exception) {
                        errorMessage = "Error en el cálculo. Verifica los valores ingresados."
                    }
                },
                modifier = Modifier.fillMaxWidth().height(60.dp)
            ) {
                Text(text = "Calcular Proteínas", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (proteinas > 0) {
                Text(
                    text = "Necesitas consumir $proteinas gramos de proteínas al día.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}