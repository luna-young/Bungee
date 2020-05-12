package com.dev.luna.bungee.intro

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import com.dev.luna.bungee.R
import org.jetbrains.anko.*

class IntroActivityUI : AnkoComponent<IntroActivity> {

    override fun createView(ui: AnkoContext<IntroActivity>): View {
        return ui.relativeLayout {
            gravity = Gravity.CENTER

            textView("Bungee!") {
                textSize = 24f
                textColorResource = R.color.colorPrimary
                typeface = Typeface.DEFAULT_BOLD
            }
        }
    }
}