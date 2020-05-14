package com.dev.luna.bungee.signup

import android.os.Bundle
import net.codephobia.ankomvvm.components.BaseActivity
import org.jetbrains.anko.setContentView

class SignupActivity : BaseActivity<SignupViewModel>() {

    override val viewModelType = SignupViewModel::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SignupActivityUI(getViewModel()) //signupActivityUI 생성자에에서 signupViewModel을 필요로 하므로 getViewModel을 통해 미리 준비된 signupViewModel의 객체를 주입
                .setContentView(this) //액티비티에 뷰를 적용하는 함수
    }

}