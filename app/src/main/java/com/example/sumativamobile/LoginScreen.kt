package com.example.sumativamobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController, onNavigateToRegister: () -> Unit, listaUsuarios: listaUsuarios){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf<String?>(null) }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Login image",
            modifier = Modifier.size(200.dp))

        Text(text = "Bienvenido", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        
        Spacer(modifier = Modifier.height(16.dp))

        
        Text(text = "Inicia Sesión con tu cuenta")

        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(value = email, onValueChange ={
            email = it.replace(Regex("\\s"), "")//Evista saltos de linea y espacios
        }, shape = RoundedCornerShape(16.dp),
            label ={ Text(text = "Dirección Email")} )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = password, onValueChange ={
            password = it.replace(Regex("\\s"), "")//Evita saltos de linea y espacios
        }, shape = RoundedCornerShape(16.dp),
            label ={ Text(text = "Contraseña")}, visualTransformation = PasswordVisualTransformation())

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                val users = listaUsuarios.getUserList()
                val user = users.find { it.email == email && it.password == password }
                if (user != null) {
                    loginError = null
                    navController.navigate("principal")
                } else {
                    loginError = "Credenciales incorrectas. Intenta nuevamente."
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

        TextButton(onClick = {navController.navigate("recuperar") }) {
            Text(text = "Olvidaste tu Contraseña?")
        }
    }

}