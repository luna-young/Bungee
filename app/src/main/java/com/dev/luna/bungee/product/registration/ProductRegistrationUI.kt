package com.dev.luna.bungee.product.registration

import android.graphics.Color
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.dev.luna.bungee.R
import com.dev.luna.bungee.api.ApiGenerator
import net.codephobia.ankomvvm.databinding.bindString
import net.codephobia.ankomvvm.databinding.bindStringEntries
import net.codephobia.ankomvvm.databinding.bindUrl
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputEditText
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener
import org.jetbrains.anko.sdk27.coroutines.textChangedListener

class ProductRegistrationUI(
        private val viewModel: ProductRegistrationViewModel
) : AnkoComponent<ProductRegistrationActivity> {

    val TAG = ProductRegistrationUI::class.java.simpleName

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

            textView("상품평 및 설명") {
                topPadding = dip(40)
                textSize = 16f
                textColorResource = R.color.colorPrimary
            }

            textInputLayout {
                topPadding = dip(20)
                textInputEditText {
                    hint = "상품명"
                    setSingleLine()
                    bindString(ui.owner, viewModel.productName)

                    //anko coroutines에서 제공하는 함수로 텍스트뷰의 텍스트가 변경되었을 때 동작하는 이벤트 리스너
                    //내부적으로는 텍스트뷰의 addTextChangedListener()를 호출해주고 있음
                    //onTextChanged의 언더스코어_는 람다의 파라미터 변수를 사용하지 않을 때 간단히 대체할 수 있는 기호
                    textChangedListener {
                        onTextChanged { _, _, _, _ ->
                            viewModel.checkProductNameLength()
                        }
                    }
                }
                textView("0/40") {
                    leftPadding = dip(4)
                    textSize = 12f
                    textColorResource = R.color.colorPrimary
                    bindString(ui.owner, viewModel.productNameLength)
                }
            }



            textInputLayout {
                topPadding = dip(20)
                textInputEditText {
                    hint = "상품 설명"
                    maxLines = 6 //UI가 늘어날 수 있는 최대 길이
                    bindString(ui.owner, viewModel.description)
                    textChangedListener {
                        onTextChanged { _, _, _, _ ->
                            viewModel.checkDescriptionLength()
                        }
                    }
                }
                textView("0/500") {
                    leftPadding = dip(4)
                    textSize = 12f
                    textColorResource = R.color.colorPrimary
                    bindString(ui.owner, viewModel.productNameLength)
                }
            }

            textView("카테고리") {
                topPadding = dip(40)
                textSize = 16f
                textColorResource = R.color.colorPrimary
            }

            verticalLayout{
                topPadding = dip(12)
                bottomPadding = dip(12)
                backgroundColor = 0xEEEEEEEE.toInt()

                spinner {
                    //bindStringEntries() 함수는 ArrayAdapter를 이용해 MutableLiveData<List<String>> 타입의 데이터를 스피너에 바인딩해줌
                    bindStringEntries(ui.owner, viewModel.categories)

                    onItemSelectedListener {
                        onItemSelected{_,_,position,_ ->
                            viewModel.onCategorySelect(position)
                        }
                    }
                }

            }.lparams(matchParent) {
                topMargin = dip(20)
            }

            textView("판매 가격") {
                topPadding = dip(40)
                textSize = 16f
                textColorResource = R.color.colorPrimary
            }

            textInputLayout {
                topPadding = dip(20)
                textInputEditText{
                    hint = "ex) 1000"
                    setSingleLine()
                    inputType = InputType.TYPE_CLASS_NUMBER
                    bindString(ui.owner, viewModel.price)
                }
            }

            button("상품 등록") {
                backgroundColorResource = R.color.colorPrimary
                textColor = Color.WHITE
                onClick {viewModel.register()}
            }.lparams(matchParent, wrapContent) {
                topMargin = dip(40)
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

        bindUrl(ui.owner, viewModel.imageUrls[imageNum]) {
            it?.let{
                scaleType = ImageView.ScaleType.CENTER_CROP

                Log.d(TAG, "@@이미지 it : $it")
                Log.d(TAG, "@@이미지 주소: ${ApiGenerator.HOST}$it")

                Glide.with(this)
                        .load("${ApiGenerator.HOST}$it")
                        .centerCrop()
                        .into(this)
            }
        }
    }.lparams(dip(60), dip(60))

}