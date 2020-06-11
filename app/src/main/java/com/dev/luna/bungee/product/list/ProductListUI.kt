package com.dev.luna.bungee.product.list

import android.util.Log
import android.view.Gravity
import androidx.recyclerview.widget.LinearLayoutManager
import net.codephobia.ankomvvm.databinding.bindPagedList
import net.codephobia.ankomvvm.databinding.bindVisibility
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

//실질적인 productListUI
class ProductListUI(
        private val viewModel: ProductListViewModel
) : AnkoComponent<ProductListFragment> {

    private val TAG = ProductListUI::class.java.simpleName
    override fun createView(ui: AnkoContext<ProductListFragment>) =
            ui.verticalLayout {

                //아이템 리스트를 표시해주기 위한 recycler view블록
                recyclerView {
                    layoutManager = LinearLayoutManager(ui.ctx) //리사이클러뷰에는 아이템들을 어떻게 배열할 것인지에 대한 layout manager를 설정해줘야
                    adapter = ProductListPagedAdapter(viewModel)
                    lparams(matchParent, matchParent)

                    bindVisibility(ui.owner, viewModel.products) {

                        Log.d(TAG, "###bindVisibility it: $it ")
                        it.isNotEmpty() //상품이 없을 경우 recycler view를 숨기고 상품이 없다는 메시지를 보여주기 위해
                    }
                    bindPagedList ( //LiveData<PagedList<T>> 타입의 객체를 바인딩하는 함수
                        ui.owner,
                        ProductListPagedAdapter(viewModel),
                        viewModel.products
                    )
                }

                textView("상품이 없습니다.") {//상품이 없을 경우 보여질 메시지
                    gravity = Gravity.CENTER
                    bindVisibility(ui.owner, viewModel.products) {
                        it.isEmpty()
                    }
                }.lparams(wrapContent, matchParent) {
                    gravity = Gravity.CENTER
                }

            }//verticalLayout
}