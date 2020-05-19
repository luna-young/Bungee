package com.dev.luna.bungee.signin

import android.graphics.Color
import android.graphics.Typeface
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.dev.luna.bungee.R
import com.dev.luna.bungee.signup.SignupActivity
import net.codephobia.ankomvvm.databinding.bindString
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputEditText
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.sdk27.coroutines.onClick

class SigninActivityUI (
        private val viewModel: SigninViewModel
) : AnkoComponent<SigninActivity> {

    private val TAG = SigninActivityUI::class.simpleName;

    override fun createView(ui: AnkoContext<SigninActivity>) = ui.linearLayout {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER_VERTICAL
        padding = dip(20)

        textView("bungee!") {
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            textSize = 24f
            typeface = Typeface.DEFAULT_BOLD
            textColorResource = R.color.colorPrimary
        }.lparams(width = matchParent) {
            bottomMargin = dip(50)
        }

        textInputLayout {
            textInputEditText {
                hint = "Email"
                setSingleLine()
                bindString(ui.owner, viewModel.email)
            }
        }.lparams(width = matchParent) {
            bottomMargin = dip(20)
        }

        //
        textInputLayout {
           textInputEditText{
               hint = "password"
               setSingleLine()
               inputType = InputType.TYPE_CLASS_TEXT or
                       InputType.TYPE_TEXT_VARIATION_PASSWORD
               bindString(ui.owner, viewModel.password)
           }

        }.lparams(width = matchParent) {
            bottomMargin = dip(20)
        }

        //

        button("로그인") {
            backgroundColorResource = R.color.colorPrimary
            textColorResource = R.color.white
            onClick {
                Log.d(TAG, "로그인 버튼 클릭")
                viewModel.signin() }
        }.lparams(width = matchParent)

        button("회원가입") {
            backgroundColor = Color.TRANSPARENT
            textColorResource = R.color.colorPrimary
            onClick { ui.startActivity<SignupActivity>() }
        }



    }

}