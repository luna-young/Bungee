package com.dev.luna.bungee.api

import retrofit2.http.GET

interface BungeeApi {

    @GET("/api/v1/hello")
    suspend fun hello(): ApiResponse<String>
    //api 인터페이스를 suspend 함수로 선언 -> 비동기 호출 사용(retrofit 2.6.0 부터는 코틀린 코루틴을 지원함)

    companion object {
        val instance = ApiGenerator()
                .generate(BungeeApi::class.java)
    }
}