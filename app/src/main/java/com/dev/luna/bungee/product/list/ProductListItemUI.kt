package com.dev.luna.bungee.product.list

import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet.PARENT_ID
import com.dev.luna.bungee.R
import com.dev.luna.bungee.view.borderBottom
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout

class ProductListItemUI : AnkoComponent<ViewGroup> {

    lateinit var imageView: ImageView
    lateinit var productName: TextView
    lateinit var price: TextView

    override fun createView(ui: AnkoContext<ViewGroup>) =
            ui.constraintLayout {
                topPadding = dip(20)
                bottomPadding = dip(20)
                clipToOutline = false
                background = borderBottom(width = 1)
                lparams(matchParent, wrapContent)

                imageView = imageView {
                    id = View.generateViewId()
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }.lparams(dip(80), dip(80))

                productName = textView("-") {
                    id = View.generateViewId()
                    textSize = 16f
                    typeface = Typeface.DEFAULT_BOLD
                    textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                }.lparams(0, wrapContent)

                price = textView("-") {
                    id = View.generateViewId()
                    textColorResource = R.color.colorAccent
                    textSize = 14f
                }

                //applyConstraintSet{} 블록에서는 id를 가진 뷰들의 상대적인 위치를 지정할 수 있다
                applyConstraintSet {
                    imageView.id {
                        connect (
                                TOP to TOP of PARENT_ID, //이미지의 윗부분이 부모 컨테이너의 윗쪽에 붙어야 한다
                                START to START of PARENT_ID margin dip(20),
                                BOTTOM to BOTTOM of PARENT_ID
                        )
                    }

                    productName.id {
                        connect (
                                TOP to TOP of imageView.id margin dip(4), //productName의 상단이 imageView의 상단을 기준으로 4dp 떨어져서 위치해야 함
                                END to END of PARENT_ID margin dip(20),
                                START to END of imageView.id margin dip(10)
                        )
                    }

                    price.id {
                        connect (
                                TOP to BOTTOM of productName.id margin dip(4),
                                START to END of imageView.id margin dip(10)
                        )
                    }
                }

            }//constraintLayout
}