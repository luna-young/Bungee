package com.dev.luna.bungee.product.list

import android.app.Application
import net.codephobia.ankomvvm.lifecycle.BaseViewModel

class ProductListViewModel(app: Application) : BaseViewModel(app) {

    //상품 리스트의 아이템을 클릭했을 때 호출되는 함수
    fun onClickItem(id: Long?) {
        toast("click $id")
    }
}