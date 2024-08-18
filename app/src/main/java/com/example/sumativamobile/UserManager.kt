package com.example.sumativamobile

object UserManager {
    private val users = mutableListOf<User>()

    fun addUser(email: String, password: String){
        if(users.none {it.email == email}){
            users.add(User(email, password))
        }
    }

    fun getUser(email: String, password: String): User? {
        return users.find{it.email == email && it.password == password}
    }

    fun getAllUsers(): List<User>{
        return users
    }
}