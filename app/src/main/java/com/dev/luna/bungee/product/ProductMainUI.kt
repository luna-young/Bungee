package com.dev.luna.bungee.product

import android.view.Gravity
import android.view.MenuItem.SHOW_AS_ACTION_ALWAYS
import androidx.drawerlayout.widget.DrawerLayout
import com.dev.luna.bungee.R
import com.dev.luna.bungee.view.borderBottom
import org.jetbrains.anko.*
import androidx.appcompat.widget.Toolbar
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.navigationView
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.drawerLayout

class ProductMainUI(
        private val viewModel: ProductMainViewModel
) : AnkoComponent<ProductMainActivity>{

    lateinit var drawerLayout: DrawerLayout
    lateinit var toolBar : Toolbar

    override fun createView(ui: AnkoContext<ProductMainActivity>) =
           ui.drawerLayout {
               drawerLayout = this

               verticalLayout {
                   toolBar = toolbar {

                       onClick{
                           viewModel.toast("툴바 클릭")
                       }

                       title = "Bungee"

                       bottomPadding = dip(1)
                       background = borderBottom(width = dip(1))

                       menu.add("Search")
                           .setIcon(R.drawable.ic_search)
                           .setShowAsAction(SHOW_AS_ACTION_ALWAYS)
                   }.lparams(matchParent, wrapContent)
               }.lparams(matchParent, matchParent)

               navigationView {
               }.lparams(wrapContent, matchParent) {
                   gravity = Gravity.START
               }

           }




}