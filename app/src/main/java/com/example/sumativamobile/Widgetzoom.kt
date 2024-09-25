package com.example.sumativamobile
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset

@Composable
fun Widgetzoom(
    modifier: Modifier = Modifier,
    onZoomChanged: (Float) -> Unit
) {
    var showProgressBar by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(50f) } // Progreso inicial (50% de zoom)
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    // Función para actualizar el progreso en función del arrastre vertical
    fun updateProgress(deltaY: Float, containerHeight: Float) {
        val newProgress = (progress - (deltaY / containerHeight) * 100).coerceIn(0f, 100f)
        progress = newProgress
        onZoomChanged(newProgress)
    }

    // Círculo arrastrable y barra de progreso
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Círculo que puede ser arrastrado
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .size(60.dp)
                .background(Color.Green, shape = CircleShape)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            offsetX += dragAmount.x
                            offsetY += dragAmount.y
                            change.consume()
                        }
                    )
                }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { showProgressBar = !showProgressBar } // Muestra/oculta la barra de progreso al tocar el círculo
                    )
                }
        ) {
            // Agregar la imagen dentro del círculo
            Image(
                painter = painterResource(id = R.drawable.zoom), // Cambia esto por el ID de tu imagen
                contentDescription = "Zoom Icon",
                modifier = Modifier
                    .size(30.dp) // Cambia este valor para ajustar el tamaño de la imagen
                    .align(Alignment.Center) // Centra la imagen dentro del círculo
            )
        }

        if (showProgressBar) {
            // Barra de progreso visible solo cuando se toca el círculo
            Box(
                modifier = Modifier
                    .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt() - 250) } // Coloca la barra sobre el círculo
                    .width(20.dp)
                    .height(200.dp)
                    .background(Color.Gray)
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { change, dragAmount ->
                            updateProgress(dragAmount, 200f)
                            change.consume()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                // Indicador de progreso dentro de la barra
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((progress * 2).dp) // Ajustar el tamaño del progreso en función del valor
                        .background(Color.Blue)
                )
            }
        }
    }
}