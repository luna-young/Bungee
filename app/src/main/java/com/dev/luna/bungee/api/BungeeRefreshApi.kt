package com.dev.luna.bungee.api

import retrofit2.http.POST
import retrofit2.http.Query

//토큰 갱신 api 정의
interface BungeeRefreshApi {

    @POST("/api/v1/refresh_token")
    suspend fun refreshToken(
            @Query("grant_type") grantType: String = "refresh_token"
    ) : ApiResponse<String>

    companion object {
        val instance = ApiGenerator()
                .generateRefreshClient(BungeeRefreshApi::class.java)
    }
}