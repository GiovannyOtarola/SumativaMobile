package com.example.sumativamobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalcularImc(loggedInEmail: String) {
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var imc by remember { mutableStateOf<Double?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    //Determinar rango de IMC
    fun obtenerRangoImc(imc: Double): String{
        return when{
            imc < 18.5 -> "Bajo peso"
            imc in 18.5..24.9 -> "Peso normal"
            imc in 25.0..29.9 -> "Sobrepeso"
            imc >= 30.0 -> "Obesidad"
            else -> ""
        }
    }

    // lambda para calcular IMC
    fun calcularImc(peso: Double, altura: Double, onResult: (Double) -> Unit) {
        if (altura > 0) {
            val resultado = peso / (altura * altura)
            onResult(resultado)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Imagen de avatar
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape) // Forma circular para el avatar
                    .border(2.dp, Color.Gray, CircleShape)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Texto del correo del usuario
            Text(
                text = loggedInEmail,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para el peso
            OutlinedTextField(
                value = peso,
                onValueChange = { peso = it },
                label = { Text("Peso (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para la altura
            OutlinedTextField(
                value = altura,
                onValueChange = { altura = it },
                label = { Text("Altura (m)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para calcular el IMC
            Button(onClick = {
                try {
                    val pesoDouble = peso.toDoubleOrNull()
                    val alturaDouble = altura.toDoubleOrNull()

                    if (pesoDouble == null || alturaDouble == null) {
                        errorMessage = "Por favor ingrese valores válidos para peso y altura."
                        return@Button
                    }

                    calcularImc(pesoDouble, alturaDouble) { resultadoImc ->
                        imc = resultadoImc
                        errorMessage = null // Limpiar mensaje de error si el cálculo es exitoso
                    }
                } catch (e: NumberFormatException) {
                    errorMessage = "Error al convertir valores. Asegurese de ingresar numeros validos."
                } catch (e: Exception) {
                    errorMessage = "Ocurrió un error inesperado: ${e.message}"
                }
            }) {
                Text(text = "Calcular IMC")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar el resultado del IMC y el rango
            imc?.let {
                Text(text = "Tu IMC es: %.2f".format(it))

                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar el rango del IMC
                val rangoImc = obtenerRangoImc(it)
                Text(text = rangoImc, fontSize = 16.sp, fontWeight = FontWeight.Bold)

                // Barra de progreso visualizando el IMC
                val sliderPosition = when {
                    it < 18.5 -> 0.2f
                    it in 18.5..24.9 -> 0.5f
                    it in 25.0..29.9 -> 0.75f
                    it >= 30.0 -> 1f
                    else -> 0f
                }

                Slider(
                    value = sliderPosition,
                    onValueChange = {},
                    valueRange = 0f..1f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    enabled = false,
                    colors = SliderDefaults.colors(
                        thumbColor = Color.Transparent,
                        activeTrackColor = when {
                            it < 18.5 -> Color.Blue
                            it in 18.5..24.9 -> Color.Green
                            it in 25.0..29.9 -> Color.Yellow
                            it >= 30.0 -> Color.Red
                            else -> Color.Gray
                        }
                    )
                )
            }
            // Mostrar mensaje de error
            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}