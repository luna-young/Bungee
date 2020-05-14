package com.dev.luna.bungee.signup

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.text.InputType
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.dev.luna.bungee.R
import net.codephobia.ankomvvm.databinding.bindString
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputEditText
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.sdk27.coroutines.onClick

class signupActivityUI (
        private val viewModel: SignupViewModel
) : AnkoComponent<SignupActivity> { //signupUI는 signupViewModel의 데이터에 의존적이므로 생성자에서 signupViewModel을 주입받는다

    override fun createView(ui: AnkoContext<SignupActivity>) =
        ui.linearLayout {//ui의 최상위 컨테이너 -> linear layout
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_VERTICAL
            padding = dip(20)

            textView("회원가입") {
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                textSize = 20f
                typeface = Typeface.DEFAULT_BOLD
                textColorResource = R.color.colorPrimary
            }.lparams(width = matchParent) {
                bottomMargin = dip(50)
            }

            textInputLayout {
                textInputEditText {
                    hint = "Email"
                    setSingleLine()
                    backgroundColorResource = R.color.colorBackground
                    bindString(ui.owner, viewModel.email)
                }
            }.lparams(width = matchParent) {
                bottomMargin = dip(20)
            }

            textInputLayout {
                textInputEditText {
                    hint = "Name"
                    setSingleLine()
                    backgroundColorResource = R.color.colorBackground
                    bindString(ui.owner, viewModel.name)
                }
            }.lparams(width = matchParent) {
                bottomMargin = dip(20)
            }

            textInputLayout {
                textInputEditText {
                    hint = "Password"
                    setSingleLine()
                    inputType = InputType.TYPE_CLASS_TEXT or
                            InputType.TYPE_TEXT_VARIATION_PASSWORD
                    backgroundColorResource = R.color.colorBackground
                    bindString(ui.owner, viewModel.password)
                }
            }.lparams(width = matchParent) {
                bottomMargin = dip(20)
            }

            button("회원가입") {
                textColorResource = R.color.colorBackground
                backgroundColorResource = R.color.colorPrimary
                onClick {
                    viewModel.signup()
                }
            }.lparams(width = matchParent)
        }


}