package com.example.pro_test.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Header


interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<GenericMessageResponse>
    @GET("me")
    suspend fun getCurrentUser(@Header("Authorization") token: String): Response<UserProfile>



}
