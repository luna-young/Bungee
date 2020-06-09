package com.dev.luna.bungee.api

import android.util.Log
import com.dev.luna.bungee.common.Prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import java.lang.Exception

//Authenticator를 상속받아 authenticate() 함수 구현
class TokenAuthenticator : Authenticator, AnkoLogger {

    val TAG = TokenAuthenticator::class.java.simpleName
    override fun authenticate(route: Route?, response: Response): Request? {
        if(response.code() == 401) { //응답코드가 401 -> 토큰 갱신 로직으로 진입
            Log.d(TAG, "----토큰 갱신 필요")
            return runBlocking { //api가 suspend 함수이므로 runBlocking{}을 이용해 코루틴 실행. runBlocking함수는 블록의 마지막 줄을 반환값으로 사용
                val tokenResponse = refreshToken() // 토큰 갱신 api 호출

                if(tokenResponse.success) {
                    Log.d(TAG, "----토큰 갱신 성공: ${tokenResponse.data}")
                    Prefs.token = tokenResponse.data // 토큰이 갱신되면 shared preferences에 새 토큰을 덮어씌움
                } else {
                    Log.e(TAG, "----토큰 갱신 실패")
                    error("토큰 갱신 실패") //토큰갱신 실패시 로그아웃을 위해 기존 토큰 제거
                    Prefs.token = null
                    Prefs.refreshToken = null
                }

                Prefs.token?.let{
                    token ->
                    response.request()
                            .newBuilder()
                            .header("Authorization", token)
                            .build()
                }
            }
        }
        else {
            Log.d(TAG, "----response code: ${response.code()}")
        }
        return null
    }


    private suspend fun refreshToken() =
            withContext(Dispatchers.IO) {
                try {
                    BungeeRefreshApi.instance.refreshToken()
                } catch (e: Exception) {
                    ApiResponse.error<String>("인증 실패")
                }
            }
}