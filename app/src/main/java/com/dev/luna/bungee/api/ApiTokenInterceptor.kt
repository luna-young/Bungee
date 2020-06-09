package com.dev.luna.bungee.api

import android.util.Log
import com.dev.luna.bungee.common.Prefs
import okhttp3.Interceptor
import okhttp3.Response
import org.jetbrains.anko.AnkoLogger

class ApiTokenInterceptor : Interceptor, AnkoLogger {
    val TAG = ApiTokenInterceptor::class.java.simpleName
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d(TAG, "----api 요청")
        val original = chain.request() //원래의 요청 객체
        //original.newBuilder() -> 새로운 요청 빌더 객체 생성
        val request  = original.newBuilder().apply {
            Prefs.token?.let {
                val token = Prefs.token
                Log.d(TAG, "----token: $token" )
                header("Authorization", it)
                Log.d(TAG, "----Authorization :$it")
            } //sharedpreferences에 토큰 값이 있는 경우 authorization 헤더에 토큰 추가


            method(original.method(), original.body()) //새 요청에 원 요청의 http메서드와 바디를 넣어줌
        }.build()

        return chain.proceed(request) //새 요청을 기반으로 http 요청에 대한 응답을 생성하여 반환환
    }
}