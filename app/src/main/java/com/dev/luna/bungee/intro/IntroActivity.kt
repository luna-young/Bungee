package com.dev.luna.bungee.intro

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.dev.luna.bungee.api.BungeeApi
import com.dev.luna.bungee.common.Prefs
import com.dev.luna.bungee.product.ProductMainActivity
import com.dev.luna.bungee.signin.SigninActivity
import com.dev.luna.bungee.signup.SignupActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity
import java.lang.Exception

class IntroActivity : Activity() {

    val TAG = IntroActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui = IntroActivityUI()
        ui.setContentView(this)

        GlobalScope.launch {
            delay(1000)

            if(Prefs.token.isNullOrEmpty()) {
                Log.d(TAG, "토큰 없음")
                 startActivity<SigninActivity>()
            }
            else {
                Log.d(TAG, "상품 메인화면 띄우기")
              startActivity<ProductMainActivity>()
            }

            finish()

        }
    }
}