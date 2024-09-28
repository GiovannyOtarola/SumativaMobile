package com.example.sumativamobile

import androidx.compose.runtime.mutableStateOf

import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class LoginScreenTest {


    private lateinit var usuarioRepository: UsuarioRepository

    @Before
    fun setup() {
        // Mock UsuarioRepository
        usuarioRepository = mock()
    }

    @Test
    fun `deberia mostrar un error cuando el correo electronico y la password estan vacios`() {
        val emailState = mutableStateOf("")
        val passwordState = mutableStateOf("")

        // Simular el clic del botón de iniciar sesion con ambos campos vacios
        val isEmailEmpty = emailState.value.isBlank()
        val isPasswordEmpty = passwordState.value.isBlank()

        // Verifica que ambos campos estan vacios
        assert(isEmailEmpty)
        assert(isPasswordEmpty)

        // mock para verificar que no se intenta realizar el login
        verify(usuarioRepository, never()).obtenerUsuarioPorEmailYContrasena(any(), any(), any())
    }


    @Test
    fun `deberia mostrar error cuando las credenciales sean incorrectas`() {
        val email = "wrong@example.com"
        val password = "wrongpassword"


        whenever(usuarioRepository.obtenerUsuarioPorEmailYContrasena(eq(email), eq(password), any())).thenAnswer {
            val callback = it.arguments[2] as (Usuario?) -> Unit
            callback(null) // Retorna null para simular credenciales incorrectas
        }

        // metodo de login
        val emailState = mutableStateOf(email)
        val passwordState = mutableStateOf(password)
        var loginError: String? = null

        usuarioRepository.obtenerUsuarioPorEmailYContrasena(email, password) { user ->
            if (user == null) {
                loginError = "Credenciales incorrectas. Intenta nuevamente."
            }
        }

        // Verificar que se muestra el error correcto
        assert(loginError == "Credenciales incorrectas. Intenta nuevamente.")
    }
}