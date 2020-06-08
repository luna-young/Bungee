package com.dev.luna.bungee.product

import android.app.Application
import android.content.Intent
import com.dev.luna.bungee.product.registration.ProductRegistrationActivity
import net.codephobia.ankomvvm.lifecycle.BaseViewModel

class ProductMainViewModel(app: Application) : BaseViewModel(app) {

    fun openRegistrationActivity() {
        startActivity<ProductRegistrationActivity> {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
    }

}