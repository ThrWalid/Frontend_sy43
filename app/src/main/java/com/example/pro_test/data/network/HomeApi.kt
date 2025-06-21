package com.example.pro_test.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeApi {
    @GET("/home")
    suspend fun getHomeData(
        @Header("Authorization") token: String
    ): Response<HomeResponse>
}
