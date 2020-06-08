package com.dev.luna.bungee.product.registration

import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.dev.luna.bungee.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class ProductRegistrationUI(
        private val viewModel: ProductRegistrationViewModel
) : AnkoComponent<ProductRegistrationActivity> {

    override fun createView(ui: AnkoContext<ProductRegistrationActivity>)
    = ui.scrollView {
        verticalLayout {
            padding = dip(20)
            clipToPadding = false //컨테이너의 패딩영역을 넘어서 위치한 자식뷰가 시각적으로 보여지도록 할지

            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER

                pickImageView(ui, 0)
                space().lparams(dip(8)) //space요소 이용 -> linear layout 내 요소들 사이에 공간 추가 가능
                pickImageView(ui, 1)
                space().lparams(dip(8))
                pickImageView(ui, 2)
                space().lparams(dip(8))
                pickImageView(ui, 3)

            }
        }
    }

    //중복되는 코드 제거하기 위해 이미지뷰를 그리는 코드를 함수로 별도 분리
    private fun _LinearLayout.pickImageView(
            ui : AnkoContext<ProductRegistrationActivity>,
            imageNum: Int
    ) = imageView(R.drawable.ic_add_image) {
        scaleType = ImageView.ScaleType.CENTER
        backgroundColor = 0xFFEEEEEE.toInt()

        onClick{viewModel.pickImage(imageNum)}
    }.lparams(dip(60), dip(60))

}