package com.dev.luna.bungee.product.registration

import android.util.Log
import com.dev.luna.bungee.api.ApiResponse
import com.dev.luna.bungee.api.BungeeApi
import com.dev.luna.bungee.api.response.ProductImageUploadResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import java.io.File
import java.lang.Exception

class ProductImageUploader: AnkoLogger {

    val TAG = ProductImageUploader::class.java.simpleName
    //파일 객체를 받아 api요청에 맞는 파라미터를 생성하고 업로드 api 호출하는 함수
    //api를 호출하기 위해 suspend함수로 선언
    suspend fun upload(imageFile: File) = try {
        Log.d(TAG, "----이미지 업로드 함수")
        val part = makeImagePart(imageFile)
        withContext(Dispatchers.IO) { //네트워크 요청이 일어나는 곳이기 때문에 IO스레드에서 수행되도록 해야
            BungeeApi.instance.uploadProductImages(part)
        }
    } catch (e: Exception) {
        error("상품 이미지 등록 오류", e)
        ApiResponse.error<ProductImageUploadResponse>(
                "$TAG: 알 수 없는 오류가 발생했습니다."
        )
    }

    private fun makeImagePart(imageFile: File) : MultipartBody.Part {
        val mediaType = MediaType.parse("multipart/form-data") //http요청이나 바디에 사용될 컨텐츠의 타입을 지정하는 mediatype객체를 만듦
        val body = RequestBody.create(mediaType, imageFile) //http 요청의 바디 생성

        return MultipartBody.Part
                .createFormData("image", imageFile.name, body)
    }
}