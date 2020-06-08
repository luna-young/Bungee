package com.dev.luna.bungee.product.registration

import com.dev.luna.bungee.api.ApiResponse
import com.dev.luna.bungee.api.BungeeApi
import com.dev.luna.bungee.api.request.ProductRegistrationRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import retrofit2.Response

/*
* 요청 데이터를 검증하고 상품 등록 api를 호출하는 역할을 하는 클래스
* 이미지 업로드와 마찬가지로 네트워크 요청이 일어나는 부분은 io스레드로 변경
* */
class ProductRegistrar : AnkoLogger {

    suspend fun register(request: ProductRegistrationRequest) = when {
        request.isNotValidName ->
            ApiResponse.error("상품명을 조건에 맞게 입력해주세요.")
        request.isNotValidDescription ->
            ApiResponse.error("상품 설명을 조건에 맞게 입력해주세요.")
        request.isNotValidPrice ->
            ApiResponse.error("가격을 조건에 맞게 입력해주세요.")
        request.isNotValidCategoryId ->
            ApiResponse.error("카테고리 아이디를 선택해주세요.")
        request.isNotValidImageIds ->
            ApiResponse.error("이미지를 한 개 이상 입력해주세요.")
        else -> withContext(Dispatchers.IO) {
            try {
                BungeeApi.instance.registerProduct(request)
            } catch (e: Exception) {
                error("상품 등록 오류", e)
                ApiResponse.error<Response<Void>>("알 수 없는 오류가 발생했습니다.")
            }
        }
    }

}