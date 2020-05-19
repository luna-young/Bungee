package com.dev.luna.bungee.signin

import android.os.Bundle
import android.os.PersistableBundle
import net.codephobia.ankomvvm.components.BaseActivity
import org.jetbrains.anko.setContentView

class SigninActivity: BaseActivity<SigninViewModel>() {
    override val viewModelType = SigninViewModel::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        SigninActivityUI(getViewModel())
                .setContentView(this)
    }
}