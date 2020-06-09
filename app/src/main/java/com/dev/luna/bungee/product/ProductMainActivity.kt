package com.dev.luna.bungee.product

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import com.dev.luna.bungee.R
import com.dev.luna.bungee.product.list.ProductListPagerAdapter
import net.codephobia.ankomvvm.components.BaseActivity
import org.jetbrains.anko.setContentView

class ProductMainActivity: BaseActivity<ProductMainViewModel>() {

    override val viewModelType = ProductMainViewModel::class
    private val ui by lazy { ProductMainUI(getViewModel()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui.setContentView(this)
        ui.viewPager.adapter = ProductListPagerAdapter(supportFragmentManager)
        ui.tabLayout.setupWithViewPager(ui.viewPager)
        setupDrawerListener()
    }


    private fun setupDrawerListener() {
        val toggle = ActionBarDrawerToggle(
                this,
                ui.drawerLayout,
                ui.toolBar,
                R.string.open_drawer_description,
                R.string.close_drawer_description
        )
        ui.drawerLayout.addDrawerListener(toggle)

        toggle.syncState() //현재 네이게이션드로어의 상태와 햄버거 버튼의 상태를 동기화
    }
}