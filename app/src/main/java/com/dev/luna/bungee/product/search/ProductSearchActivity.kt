package com.dev.luna.bungee.product.search

import android.os.Bundle
import android.view.MenuItem
import net.codephobia.ankomvvm.components.BaseActivity
import org.jetbrains.anko.setContentView

class ProductSearchActivity : BaseActivity<ProductSearchViewModel>() {

    override val viewModelType = ProductSearchViewModel::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //인텐트를 통해 검색창에 입력한 키워드를 받아옴
        val keyword = intent.getStringExtra(KEYWORD)
        val viewModel = getViewModel().apply{
            this.keyword = keyword //인텐트를 통해 받은 키워드를 ProductSearchViewModel에 전달
        }

        ProductSearchUI(viewModel).setContentView(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = keyword //앱바의 타이틀을 키워드로 변경해줌
    }//onCreate

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }



    companion object {
        const val KEYWORD = "keyword"
    }
}