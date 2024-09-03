import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sumativamobile.R
import com.example.sumativamobile.User
import com.example.sumativamobile.listaUsuarios



@Composable
fun RegistroScreen(onRegistrationComplete: () -> Unit, listaUsuarios: listaUsuarios) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var registrationError by remember { mutableStateOf<String?>(null) }
    var isEmailValid by remember { mutableStateOf(true) }

    // Expresión  para validar formato de email + el dominio
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.registro), contentDescription = "Registro image",
            modifier = Modifier.size(150.dp))

        Text(text = "Registro", fontSize = 40.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it.replace(Regex("\\s"), "")// Evitar saltos de linea y espacios
                isEmailValid = emailRegex.matches(it)  // Validar el formato de email
            },
            shape = RoundedCornerShape(16.dp),
            label = { Text(text = "Dirección Email") },
            isError = !isEmailValid
        )
        if (!isEmailValid) {
            Text(text = "Formato de email inválido", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it.replace(Regex("\\s"), "") },//Evitar saltos de linea y espacios
            shape = RoundedCornerShape(16.dp),
            label = { Text(text = "Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it.replace(Regex("\\s"), "")},//Evitar saltos de linea y espacios
            shape = RoundedCornerShape(16.dp),
            label = { Text(text = "Confirmar Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            if (isEmailValid) {
                if (password == confirmPassword) {
                    val users = listaUsuarios.getUserList().toMutableList()
                    if (users.none { it.email == email }) {
                        users.add(User(email, password))
                        listaUsuarios.saveUserList(users)
                        onRegistrationComplete()
                    } else {
                        registrationError = "El usuario ya existe."
                    }
                } else {
                    registrationError = "Las contraseñas no coinciden."
                }
            } else {
                registrationError = "Formato de email inválido."
            }
        }) {
            Text(text = "Registrarse")
        }

        registrationError?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}
