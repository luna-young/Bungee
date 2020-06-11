package com.dev.luna.bungee.product.list

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.paging.DataSource
import com.dev.luna.bungee.api.response.ProductListItemResponse
import com.dev.luna.bungee.product.detail.ProductDetailActivity
import net.codephobia.ankomvvm.lifecycle.BaseViewModel
import org.jetbrains.anko.error

class ProductListViewModel(
        app: Application
) : BaseViewModel(app),
    ProductListPagedAdapter.ProductLiveDataBuilder,
    ProductListPagedAdapter.OnItemClickListener {

    private val TAG = ProductListViewModel::class.java.simpleName

    var categoryId: Int = -1 //각 프래그먼트에서 표시돼야 할 아이템들의 카테고리 id. -1일 경우 dataSourceFactory에서 오류 로그를 기록
    val products = buildPagedList() //데이터소스로부터 아이템 리스트를 어떻게 가져올 것인지. 페이지 사이즈는 10으로

    override fun createDataSource(): DataSource<Long, ProductListItemResponse> {

        Log.d(TAG, "##createDataSource categoryId: $categoryId")
        if(categoryId == -1)
            error (
                    "categoryId가 설정되지 않았습니다.",
                    IllegalStateException("categoryId is -1")
            )

        return ProductListItemDataSource(categoryId)
    }

    override fun onClickProduct(productId: Long?) {
        //startActivity<ProductDetailActivity> {
          //  flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            //putExtra(ProductDetailActivity.PRODUCT_ID, productId)
        //}
    }

}