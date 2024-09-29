package com.example.sumativamobile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Usuarios(usuarioRepository: UsuarioRepository,navController: NavController) {
    // Estado para almacenar la lista de usuarios
    val users = remember { mutableStateOf<List<Usuario>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    val context = LocalContext.current

    // Recupera usuarios desde Firestore
    LaunchedEffect(Unit) {
        usuarioRepository.obtenerTodosUsuarios { usuarioList ->
            users.value = usuarioList
            isLoading.value = false
        }
    }

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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Usuarios Registrados", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            //indicador de carga mientras se obtienen los usuarios
            if (isLoading.value) {
                CircularProgressIndicator()
            } else {
                LazyColumn {
                    items(users.value) { user ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(text = "Email: ${user.email}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "Contraseña: ${user.password}", fontSize = 16.sp)

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Button(
                                        onClick = {
                                            navController.navigate("EditarUsuarioScreen/${user.id}")
                                        },
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(end = 4.dp) // Espaciado entre botones
                                    ) {
                                        Text(text = "Editar")
                                    }

                                    Button(
                                        onClick = {
                                            // Acción para eliminar el usuario
                                            usuarioRepository.eliminarUsuario(user.id!!) { success ->
                                                if (success) {
                                                    // Actualiza la lista de usuarios despuEs de eliminar
                                                    usuarioRepository.obtenerTodosUsuarios { updatedUserList ->
                                                        users.value = updatedUserList
                                                    }
                                                } else {
                                                    Toast.makeText(context, "Error al eliminar el usuario", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        },
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(start = 4.dp)
                                    ) {
                                        Text(text = "Eliminar")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}