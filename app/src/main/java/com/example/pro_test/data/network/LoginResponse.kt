package com.example.pro_test.data.network

data class User(
    val id: Int,
    val username: String
)

data class LoginResponse(
    val token: String,
    val user: User
)
