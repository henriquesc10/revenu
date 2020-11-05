package com.example.receitas.model

class User {
    var email: String? = null
    var password: String? = null

    override fun toString(): String {
        return "user(email='$email', password='$password')"
    }

}