package com.example.pro_test.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8080/" // ← استبدلها لو عندك IP آخر

    /**
     * يعطينا ApiService كل مرة بالـ token الحالي في الهيدر.
     */
    fun getService(): ApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                AuthManager.token?.let { jwt ->
                    builder.addHeader("Authorization", "Bearer $jwt")
                }
                chain.proceed(builder.build())
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}