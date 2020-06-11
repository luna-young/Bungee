package com.dev.luna.bungee.product.detail

import android.os.Bundle
import android.view.MenuItem
import net.codephobia.ankomvvm.components.BaseActivity
import org.jetbrains.anko.setContentView

class ProductDetailActivity : BaseActivity<ProductDetailViewModel>() {

    override val viewModelType = ProductDetailViewModel::class


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //뒤로 가기 버튼을 위해서 앱바를 사용
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val viewModel = getViewModel()
        val productId = intent.getLongExtra(PRODUCT_ID, -1)

        viewModel.loadDetail(productId)
        ProductDetailUI(getViewModel()).setContentView(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            when(item.itemId) {
                android.R.id.home -> onBackPressed()
                else -> {}
            }
        }
        return true
    }

    companion object {
        val PRODUCT_ID = "productId"
    }
}