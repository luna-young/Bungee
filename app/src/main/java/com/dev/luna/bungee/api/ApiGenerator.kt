package com.dev.luna.bungee.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiGenerator : AnkoLogger{

    val TAG = ApiGenerator.javaClass.simpleName

    fun<T> generate(api:Class<T>): T = Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient())
            .build()
            .create(api)

    //토큰 갱신용 api 객체를 만들기 위한 제너레이트 함수 추가
    //클라이언트는 refreshClient() 함수를 통해 생성
    fun<T> generateRefreshClient(api: Class<T>) : T = Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(refreshClient())
            .build()
            .create(api)


    private fun httpClient() =
            OkHttpClient.Builder().apply {
                addInterceptor(httpLogginInterceptor())
                Log.d( TAG,"----ApiTokenInterceptor" )
                addInterceptor(ApiTokenInterceptor())
                Log.d(TAG, "----TokenAuthenticator")
                authenticator(TokenAuthenticator())
            }.build()

    //새로운 http 클라이언트 생성, 앞서 만든 TokenRefreshInterceptor 객체를 인터셉터로 추가
    private fun refreshClient() =
            OkHttpClient.Builder().apply {
                addInterceptor(httpLogginInterceptor())
                addInterceptor(TokenRefreshInterceptor())
            }.build()

    private fun httpLogginInterceptor() =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

    companion object {
        const val HOST = "http://54.180.26.152"
    }
}