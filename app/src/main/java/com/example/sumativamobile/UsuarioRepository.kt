package com.example.sumativamobile



import com.google.firebase.firestore.FirebaseFirestore


class UsuarioRepository {
    private val db = FirebaseFirestore.getInstance()
    private val usersRef = db.collection("usuarios")

    fun crearUsuario(usuario: Usuario, onComplete: (Boolean) -> Unit) {
        // Genera un ID Unico para el usuario
        val userId = db.collection("usuarios").document().id
        usuario.id = userId // Asigna el ID al usuario

        // Almacena el usuario en la colección "usuarios"
        db.collection("usuarios").document(userId).set(usuario)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }

    // Leer un usuario por ID
    fun obtenerUsuario(userId: String, onComplete: (Usuario?) -> Unit) {
        usersRef.document(userId).get().addOnCompleteListener { task ->
            if (task.isSuccessful && task.result != null) {
                val document = task.result
                val usuario = Usuario(
                    id = document.id,
                    email = document.getString("email"),
                    password = document.getString("password")
                )
                onComplete(usuario)
            } else {
                onComplete(null)
            }
        }
    }

    // Actualizar un usuario
    fun actualizarUsuario(usuario: Usuario, onComplete: (Boolean) -> Unit) {
        usuario.id?.let { userId ->
            usersRef.document(userId).set(usuario).addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
        } ?: onComplete(false)
    }

    // Eliminar un usuario
    fun eliminarUsuario(userId: String, onComplete: (Boolean) -> Unit) {
        usersRef.document(userId).delete().addOnCompleteListener { task -> onComplete(task.isSuccessful) }
    }

    // Obtener todos los usuarios
    fun obtenerTodosUsuarios(onComplete: (List<Usuario>) -> Unit) {
        usersRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val usuarios = mutableListOf<Usuario>()
                task.result?.forEach { document ->
                    val usuario = Usuario(
                        id = document.id,
                        email = document.getString("email"),
                        password = document.getString("password")
                    )
                    usuarios.add(usuario)
                }
                onComplete(usuarios)
            } else {
                onComplete(emptyList())
            }
        }
    }

    fun obtenerUsuarioPorEmail(email: String, onComplete: (Usuario?) -> Unit) {
        usersRef.whereEqualTo("email", email).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    val usuario = Usuario(
                        id = document.id,
                        email = document.getString("email"),
                        password = document.getString("password")
                    )
                    onComplete(usuario)
                    return@addOnCompleteListener
                }
                onComplete(null)
            } else {
                onComplete(null)
            }
        }
    }

    fun obtenerUsuarioPorEmailYContrasena(email: String, password: String, onComplete: (Usuario?) -> Unit) {
        usersRef.whereEqualTo("email", email).whereEqualTo("password", password).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && !task.result.isEmpty) {
                    val document = task.result.documents[0]
                    val usuario = Usuario(
                        id = document.id,
                        email = document.getString("email"),
                        password = document.getString("password")
                    )
                    onComplete(usuario)
                } else {
                    onComplete(null)
                }
            }
    }
}