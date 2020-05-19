package com.dev.luna.bungee.product

import android.graphics.Typeface
import android.view.View
import com.dev.luna.bungee.R
import com.dev.luna.bungee.common.Prefs
import com.dev.luna.bungee.view.borderBottom
import org.jetbrains.anko.*

//네이게이션 헤더 --복잡해질 수 있으므로 코드 분리
class ProductMainNavHeader : AnkoComponent<View> {

    override fun createView(ui: AnkoContext<View>) =
            ui.verticalLayout{

                padding = dip(20)
                background = borderBottom(width = dip(1))

                imageView(R.drawable.ic_elephant_64)
                        .lparams(dip(54), dip(54))

                textView(Prefs.userName) {
                    topPadding = dip(8)
                    textSize = 20f
                    typeface  = Typeface.DEFAULT_BOLD
                }
            }
}