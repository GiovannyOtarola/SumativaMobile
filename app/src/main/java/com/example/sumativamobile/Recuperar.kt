package com.example.sumativamobile

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Recuperar(context: Context) {
    var email by remember { mutableStateOf("") }
    val userManager = listaUsuarios(context)

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.recuperar), contentDescription = "Recuperar image",
            modifier = Modifier.size(200.dp))

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
            val users = userManager.getUserList()
            val user = users.find { it.email == email }
            val password = user?.password ?: "No se encontró el usuario"
            Toast.makeText(context, "Contraseña: $password", Toast.LENGTH_LONG).show()
        }) {
            Text(text = "Mostrar Contraseña")
        }
    }
}