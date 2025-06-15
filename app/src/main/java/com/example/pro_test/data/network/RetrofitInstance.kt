package com.example.pro_test.data.network
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // 👇 Base URL de ton backend Ktor
    private const val BASE_URL = "http://10.0.2.2:8080/"

    // Intercepteur pour afficher les requêtes HTTP dans Logcat
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    // Retrofit prêt à l'emploi
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Accès à tes endpoints API
    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }
}
