package com.dev.luna.bungee.api

import com.dev.luna.bungee.api.request.SigninRequest
import com.dev.luna.bungee.api.request.SignupRequest
import com.dev.luna.bungee.api.response.SigninResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BungeeApi {

    @GET("/api/v1/hello")
    suspend fun hello(): ApiResponse<String>
    //api 인터페이스를 suspend 함수로 선언 -> 비동기 호출 사용(retrofit 2.6.0 부터는 코틀린 코루틴을 지원함)

    @GET("/api/v1/users")
    suspend fun signup(@Body signupRequest: SignupRequest)
        : ApiResponse<Void>

    @POST("/api/v1/signin")
    suspend fun signin(@Body signinRequest: SigninRequest)
    :ApiResponse<SigninResponse>

    companion object {
        val instance = ApiGenerator()
                .generate(BungeeApi::class.java)
    }
}