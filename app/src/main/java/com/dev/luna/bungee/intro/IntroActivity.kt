package com.dev.luna.bungee.intro

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.dev.luna.bungee.api.BungeeApi
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.setContentView
import java.lang.Exception

class IntroActivity : Activity() {

    val TAG = IntroActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui = IntroActivityUI()
        ui.setContentView(this)

        runBlocking {
            try {
                val response = BungeeApi.instance.hello()
                Log.d(TAG, response.data)
            } catch (e: Exception) {
                Log.e(TAG, "Hello Api 호출 오류", e)
            }
        }

    }
}