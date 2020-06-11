package com.dev.luna.bungee.product.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.codephobia.ankomvvm.components.BaseFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

class ProductListFragment : BaseFragment<ProductListViewModel>() {

    override val viewModelType = ProductListViewModel::class

    //ProductListFragment에서 가지고 있어야할 프로퍼티인 categoryId, title
    val categoryId get() = arguments?.getInt("categoryId")
            ?: throw IllegalStateException("categoryId 없음")
    val title get() = arguments?.getString("title")
            ?:throw IllegalStateException("title 없음")

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
            val viewModel = getViewModel()
            viewModel.categoryId = categoryId

            return ProductListUI(viewModel)
                    .createView(AnkoContext.create(ctx, this))
    }

    companion object {
        //안드로이드 프레임워크에서는 fragment복원 시 기본 생성자를 사용하기 때문에
        //권장되는 방법은 기본 생성자를 이용해 객체를 생성한 후 Bundle을 이용해 arguments에 값을 전달하는 것
        fun newInstance(categoryId: Int, title:String) =
                ProductListFragment().apply {
                    arguments = Bundle().also {
                        it.putInt("categoryId", categoryId)
                        it.putString("title", title)
                    }
                }
    }

}

