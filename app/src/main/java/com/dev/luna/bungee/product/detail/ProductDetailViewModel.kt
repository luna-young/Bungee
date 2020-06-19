package com.dev.luna.bungee.product.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dev.luna.bungee.api.ApiResponse
import com.dev.luna.bungee.api.BungeeApi
import com.dev.luna.bungee.api.response.ProductResponse
import com.dev.luna.bungee.product.ProductStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.codephobia.ankomvvm.databinding.addAll
import net.codephobia.ankomvvm.lifecycle.BaseViewModel
import org.jetbrains.anko.error
import java.lang.Exception
import java.text.NumberFormat

class ProductDetailViewModel(app: Application) : BaseViewModel(app) {

    private val TAG = ProductDetailViewModel::class.java.simpleName

    val productId: Long? = null

    val productName = MutableLiveData("-")
    val description = MutableLiveData("-")
    val price = MutableLiveData("-")
    val imageUrls: MutableLiveData<MutableList<String>> =
            MutableLiveData(mutableListOf())

    //액티비티가 실행된 후 상품 정보를 가져오기 위한 함수
    //suspend함수인 api를 호출하는 부분이 포함돼있기 때문에 코루틴으로 감싸줌
    fun loadDetail(id: Long) = viewModelScope.launch(Dispatchers.Main) {
        try {
            val response = getProduct(id)
            if(response.success && response.data != null) {
                updateViewData(response.data)
            } else {
                toast(response.message?: "알 수 없는 오류가 발생했습니다.")
            }
        } catch (e:Exception) {

        }
    }

    private suspend fun getProduct(id: Long) = try {
        BungeeApi.instance.getProduct(id)
    } catch (e: Exception) {
        error("상품 정보를 가져오는 중 오류 발생", e)
        ApiResponse.error<ProductResponse>("상품 정보를 가져오는 중 오류가 발생했습니다.")
    }

    //상품 정보를 가져온 후 그 정보들을 뷰에 보여주기 위하 프로퍼티들을 수정
    private fun updateViewData(product: ProductResponse) {
        val commaSeparatedPrice =
                NumberFormat.getInstance().format(product.price)
        val soldOutString =
                if(ProductStatus.SOLD_OUT == product.status) "(품절)" else ""

        productName.value = product.name
        description.value = product.description
        price.value =
                "${commaSeparatedPrice} $soldOutString"
        Log.d(TAG, "상품 이미지주소: ${product.imagePaths}")
        imageUrls.addAll(product.imagePaths)
    }

    fun openInquiryActivity() {
        toast("상품 문의 - productId = $productId")
    }
}