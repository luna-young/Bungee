package com.dev.luna.bungee.product.detail

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet.PARENT_ID
import com.dev.luna.bungee.R
import net.codephobia.ankomvvm.databinding.bindItem
import net.codephobia.ankomvvm.databinding.bindString
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.BOTTOM
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.TOP
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.viewPager

class ProductDetailUI(
        private val viewModel: ProductDetailViewModel
) : AnkoComponent<ProductDetailActivity> {

    override fun createView(ui: AnkoContext<ProductDetailActivity>) =
            ui.constraintLayout {
                val content = scrollView {//스크롤뷰는 하나의 자식만 가져야 하는 특성이 있음 -> 내부에 바로 linear layout 등을 배치해 스크롤이 돼야 하는 모든 뷰를 배치함
                    id = View.generateViewId()
                    lparams(matchParent, 0)

                    verticalLayout {
                        constraintLayout {
                            lparams(matchParent, matchParent)
                            viewPager {//이미지 슬라이드가 될 뷰페이저
                                backgroundColor = Color.GRAY
                                adapter = ImageSliderAdapter().apply {
                                    bindItem(ui.owner, viewModel.imageUrls) {
                                        //bindItem()을 통해 아이템 리스트가 변경됐을 때 호출될 콜백에서 updateItems()를 호출
                                        //viewPager의 bindItem()함수는 AnkoMVVM에 라이브러리에 정의된 함수로,
                                        //데이터셋이 변경됐을 때 콜백을 호출해주는 역할만 함
                                        updateItems(it)
                                    }
                                }
                            }.lparams(matchParent, dip(0)) {
                                dimensionRatio = "1:1" //constraint layout 내부의 뷰에 대한 가로 세로 비율
                            }
                        }

                        verticalLayout {
                            padding = dip(20)

                            textView {
                                textSize = 16f
                                typeface = Typeface.DEFAULT_BOLD
                                textColor = Color.BLACK
                                bindString(ui.owner, viewModel.productName)
                            }.lparams(matchParent, wrapContent)

                            textView {
                                textSize = 16f
                                typeface = Typeface.DEFAULT_BOLD
                                textColorResource = R.color.colorAccent
                                bindString(ui.owner, viewModel.price)
                            }.lparams(matchParent, wrapContent) {
                                topMargin = dip(20)
                            }

                            textView("상품설명") {
                                textSize = 16f
                                typeface = Typeface.DEFAULT_BOLD
                                textColorResource = R.color.colorPrimary
                            }.lparams(matchParent, wrapContent) {
                                topMargin = dip(20)
                            }

                            textView {
                                textSize = 14f
                                textColor = Color.BLACK
                                bindString(ui.owner, viewModel.description)
                            }.lparams(matchParent) {
                                topMargin = dip(20)
                            }
                        }
                    }
                }

                val fixedBar = linearLayout {//스크롤과 별개로 하단에 고정될 상품문의 버튼이 있는 ㅏ
                    id = View.generateViewId()
                    padding = dip(10)
                    gravity = Gravity.END
                    backgroundColor = Color.DKGRAY
                    lparams(matchParent, wrapContent)

                   /* button("상품 문의") {
                        onClick { viewModel.openInquiryActivity() }
                    }*/
                }

                //스크롤뷰 영역과 하단바 영역에 대한 위치 설정
               /* applyConstraintSet {
                    fixedBar.id {
                        connect(
                                BOTTOM to BOTTOM of PARENT_ID
                        )
                    }

                    content.id {
                        connect(
                                TOP to TOP of PARENT_ID,
                                BOTTOM to TOP of fixedBar
                        )
                    }
                }*/
            }

}