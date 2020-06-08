package com.dev.luna.bungee.product

import android.app.Application
import android.content.Intent
import com.dev.luna.bungee.product.registration.ProductRegistrationActivity
import net.codephobia.ankomvvm.lifecycle.BaseViewModel

class ProductMainViewModel(app: Application) : BaseViewModel(app) {

    fun openRegistrationActivity() {
        //todo 상품 등록 ui가 준비되면 해당 액티비티를 열도록 수정
        startActivity<ProductRegistrationActivity> {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
    }

}