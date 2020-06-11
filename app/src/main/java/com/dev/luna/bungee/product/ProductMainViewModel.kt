package com.dev.luna.bungee.product

import android.app.Application
import android.content.Intent
import android.util.Log
import com.dev.luna.bungee.product.registration.ProductRegistrationActivity
import com.dev.luna.bungee.product.search.ProductSearchActivity
import net.codephobia.ankomvvm.lifecycle.BaseViewModel

class ProductMainViewModel(app: Application) : BaseViewModel(app) {

    private val TAG = ProductMainViewModel::class.simpleName
    fun openSearchActivity(keyword: String?) {
        keyword?.let {
            Log.d(TAG, "검색 키워드: $keyword")
            startActivity<ProductSearchActivity> {
                putExtra(ProductSearchActivity.KEYWORD, keyword)
            }
        } ?: toast("검색 키워드를 입력해주세요.")
    }


    fun openRegistrationActivity() {
        startActivity<ProductRegistrationActivity> {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
    }
}