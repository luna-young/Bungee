package com.dev.luna.bungee.api

import com.dev.luna.bungee.api.request.ProductRegistrationRequest
import com.dev.luna.bungee.api.request.SigninRequest
import com.dev.luna.bungee.api.request.SignupRequest
import com.dev.luna.bungee.api.response.ProductImageUploadResponse
import com.dev.luna.bungee.api.response.SigninResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface BungeeApi {

    @GET("/api/v1/hello")
    suspend fun hello(): ApiResponse<String>
    //api 인터페이스를 suspend 함수로 선언 -> 비동기 호출 사용(retrofit 2.6.0 부터는 코틀린 코루틴을 지원함)

    @POST("/api/v1/users")
    suspend fun signup(@Body signupRequest: SignupRequest)
        : ApiResponse<Void>

    @POST("/api/v1/signin")
    suspend fun signin(@Body signinRequest: SigninRequest)
    :ApiResponse<SigninResponse>

    @Multipart //파일업로드가 필요한 api에는 해당 애너테이션을 붙여 이 요청의 바디가 multi-part임을 알려야 함
    @POST("/api/v1/product_images")
    suspend fun uploadProductImages (
            @Part images: MultipartBody.Part //@Multipart로 설정된 api 요청의 파라미터들은 @Part 애너테이션을 붙여 이 파라미터가 multi-part욫ㅇ의 일부임을 알려야 함
    ) : ApiResponse<ProductImageUploadResponse>

    @POST("/api/v1/products")
    suspend fun registerProduct(
            @Body request: ProductRegistrationRequest
    ): ApiResponse<Response<Void>>

    companion object {
        val instance = ApiGenerator()
                .generate(BungeeApi::class.java)
    }
}