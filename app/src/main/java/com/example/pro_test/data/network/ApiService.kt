package com.example.pro_test.data.network

import retrofit2.http.*

interface ApiService {

    // ===================== Auth ===================== //
    @POST("auth/login")
    suspend fun login(@Body req: LoginRequest): LoginResponse

    // ===================== Contacts ================= //
    @GET("friends")
    suspend fun getContacts(): List<ContactResponse>

    // ===================== Messages ================= //
    @GET("messages/{userId}")
    suspend fun getMessages(@Path("userId") userId: Int): List<MessageResponse>

    @POST("messages/send")
    suspend fun sendMessage(@Body req: MessageRequest): MessageResponse
}