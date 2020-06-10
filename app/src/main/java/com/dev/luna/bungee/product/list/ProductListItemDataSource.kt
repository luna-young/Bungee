package com.dev.luna.bungee.product.list

import androidx.paging.PageKeyedDataSource
import com.dev.luna.bungee.App
import com.dev.luna.bungee.api.ApiResponse
import com.dev.luna.bungee.api.BungeeApi
import com.dev.luna.bungee.api.response.ProductListItemResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.toast

class ProductListItemDataSource (
        private val categoryId: Int?
) : PageKeyedDataSource<Long, ProductListItemResponse>() { //PageKeyedDataSource를 상속받는 클래스. 초기 데이터를 로드하고, 이전/이후의 데이터를 로드하기 위한 콜백으로 구성


    //초기 데이터를 로드하는 콜백. 상품을 최신순으로 읽어와야 하므로 id를 Long.MAX_VALUE로 사용
    override fun loadInitial(params: LoadInitialParams<Long>,
                             callback: LoadInitialCallback<Long, ProductListItemResponse>
    ) {
        val response = getProducts(Long.MAX_VALUE, NEXT)
        if(response.success) {
            response.data?.let{
                if(it.isEmpty()) { //api로부터 데이터 수신 후 callback.onResult()를 통해 데이터가 추가되었음을 알려야
                    callback.onResult(it, it.first().id, it.last().id)
                }
            }
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                showErrorMessage(response)
            }
        }
    }

    //다음 데이터(과거 데이터)를 불러오기  위해 사용됨
    override fun loadAfter(params: LoadParams<Long>,
                           callback: LoadCallback<Long, ProductListItemResponse>
    ) {
        val response = getProducts(params.key, NEXT)
        if(response.success) {
            response.data?.let{
                if(it.isEmpty())
                    callback.onResult(it, it.last().id) //이전의 데이터 목록을 불러오기 위해 두번째 파라미터로 받은 api로부터 받은 마지막 데이터의 id를 넘겨줌
            }
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                showErrorMessage(response)
            }
        }
    }

    //더 나중에 등록된 데이터를 불러오기 위해 사용됨
    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, ProductListItemResponse>
    ) {
        val response = getProducts(params.key, PREV)
        if(response.success) {
            response.data?.let{
                if(it.isEmpty())
                    callback.onResult(it, it.first().id)
            }
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                showErrorMessage(response)
            }
        }
    }


    private fun getProducts(id: Long, direction: String) = runBlocking {
        try {
            BungeeApi.instance.getProducts(id, categoryId, direction)
        } catch (e: Exception) {
            ApiResponse.error<List<ProductListItemResponse>>(
                    "알 수 없는 오류가 발생했습니다 @ getProducts"
            )
        }
    }

    private fun showErrorMessage (
            response : ApiResponse<List<ProductListItemResponse>>
    ) {
        App.instance.toast(
                response.message ?: "알 수 없는 오류가 발생했습니다."
        )
    }

    companion object {
        private const val NEXT = "next"
        private const val PREV = "prev"
    }

}