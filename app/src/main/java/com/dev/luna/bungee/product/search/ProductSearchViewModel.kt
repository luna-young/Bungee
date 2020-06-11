package com.dev.luna.bungee.product.search

import android.app.Application
import android.content.Intent
import com.dev.luna.bungee.product.detail.ProductDetailActivity
import com.dev.luna.bungee.product.list.ProductListItemDataSource
import com.dev.luna.bungee.product.list.ProductListPagedAdapter
import net.codephobia.ankomvvm.lifecycle.BaseViewModel

class ProductSearchViewModel(app: Application) :
    BaseViewModel(app),
    ProductListPagedAdapter.ProductLiveDataBuilder,
    ProductListPagedAdapter.OnItemClickListener
{
    var keyword: String? = null //검색결과 페이지에서는 검색키워드 변경이 불가능하므로, 뷰모델 클래스에서 keyword 변수를 String 형태로 가지고 있음
    val products = buildPagedList()

    override fun createDataSource() =
            ProductListItemDataSource(null, keyword)

    override fun onClickProduct(productId: Long?) {
        startActivity<ProductDetailActivity> {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(ProductDetailActivity.PRODUCT_ID, productId)
        }
    }

}