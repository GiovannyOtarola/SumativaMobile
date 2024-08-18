package com.example.sumativamobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun Principal(listaUsuarios: listaUsuarios) {
    val users = listaUsuarios.getUserList()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Esta pantalla en un futuro cercano sera completamente diferente", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Image(painter = painterResource(id = R.drawable.a), contentDescription = "",
            modifier = Modifier.size(200.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Usuarios Registrados", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // Tabla de usuarios registrados
        LazyColumn {
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Email", fontWeight = FontWeight.Bold)
                    Text(text = "Contraseña", fontWeight = FontWeight.Bold)
                }
            }
            items(users) { user ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = user.email)
                    Text(text = user.password)
                }
            }
        }
    }
}