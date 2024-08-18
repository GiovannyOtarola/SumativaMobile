package com.example.sumativamobile

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class listaUsuarios(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    private val gson = Gson()

    // Guardar la lista de usuarios como JSON
    fun saveUserList(users: List<User>) {
        val json = gson.toJson(users)
        sharedPreferences.edit().apply {
            putString("USER_LIST", json)
            apply()
        }
    }

    // Obtener la lista de usuarios como JSON
    fun getUserList(): List<User> {
        val json = sharedPreferences.getString("USER_LIST", null) ?: return emptyList()
        val type = object : TypeToken<List<User>>() {}.type
        return gson.fromJson(json, type)
    }




}