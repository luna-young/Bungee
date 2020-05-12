package com.dev.luna.bungee.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiGenerator {

    fun<T> generate(api:Class<T>): T = Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient())
            .build()
            .create(api)


    private fun httpClient() =
            OkHttpClient.Builder().apply {
                addInterceptor(httpLogginInterceptor())
            }.build()

    private fun httpLogginInterceptor() =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

    companion object {
        const val HOST = "http://10.0.2.2:8080"
    }
}