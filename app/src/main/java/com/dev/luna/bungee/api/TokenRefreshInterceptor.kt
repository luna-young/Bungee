package com.dev.luna.bungee.api

import android.content.Intent
import android.util.Log
import com.dev.luna.bungee.App
import com.dev.luna.bungee.common.Prefs
import com.dev.luna.bungee.signin.SigninActivity
import okhttp3.Interceptor
import okhttp3.Response
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.intentFor

class TokenRefreshInterceptor : Interceptor, AnkoLogger{

    val TAG = TokenRefreshInterceptor::class.java.simpleName

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d(TAG, "----토큰 갱신요청")
        val original = chain.request()
        val request = original.newBuilder().apply {

            //api 토큰과 마찬가지로 sharedpreferences에 refresh token이 존재하는 경우 authorization 헤더에 추가해준다
            Prefs.refreshToken?.let {

                val refreshToken = Prefs.refreshToken
                Log.d(TAG, "-----Prefs.refreshToken: $refreshToken")

                header("Authorization", it)
                method(original.method(), original.body())
            }
        }.build()
        val response = chain.proceed(request)

        if(response.code() == 401) { //응답객체를 받아 응답코드가 401인 경우 로그인 화면으로 이동시켜준다
            App.instance.run{
                val intent = intentFor<SigninActivity>().apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
            }
        }

        return response
    }
}