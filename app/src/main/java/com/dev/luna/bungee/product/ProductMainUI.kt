package com.dev.luna.bungee.product

import android.app.usage.UsageEvents.Event.NONE
import android.view.Gravity
import android.view.MenuItem
import android.view.MenuItem.SHOW_AS_ACTION_ALWAYS
import androidx.drawerlayout.widget.DrawerLayout
import com.dev.luna.bungee.R
import com.dev.luna.bungee.view.borderBottom
import org.jetbrains.anko.*
import androidx.appcompat.widget.Toolbar
import com.dev.luna.bungee.common.Prefs
import com.dev.luna.bungee.signin.SigninActivity
import com.google.android.material.navigation.NavigationView
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.navigationView
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.drawerLayout

class ProductMainUI(
        private val viewModel: ProductMainViewModel
) : AnkoComponent<ProductMainActivity> ,
    NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout: DrawerLayout
    lateinit var toolBar : Toolbar
    lateinit var navigationView: NavigationView

    override fun createView(ui: AnkoContext<ProductMainActivity>) =
           ui.drawerLayout {
               drawerLayout = this

               verticalLayout {
                   toolBar = toolbar {

                       title = "Bungee"
                       bottomPadding = dip(1)
                       background = borderBottom(width = dip(1))

                       menu.add("Search")
                           .setIcon(R.drawable.ic_search)
                           .setShowAsAction(SHOW_AS_ACTION_ALWAYS)
                   }.lparams(matchParent, wrapContent)
               }.lparams(matchParent, matchParent)

               navigationView = navigationView {
                   ProductMainNavHeader()
                           .createView(AnkoContext.create(context, this))
                           .let(::addHeaderView)

                   menu.apply {
                       //groupId, itemId, order, title
                       add(NONE, MENU_ID_INQUIRY, NONE, "내 문의").apply {
                           setIcon(R.drawable.ic_chat)
                       }
                       add(NONE, MENU_ID_INQUIRY, NONE, "로그아웃").apply {
                           setIcon(R.drawable.ic_signout)
                       }
                   }
                   setNavigationItemSelectedListener(this@ProductMainUI)

               }.lparams(wrapContent, matchParent) {
                   gravity = Gravity.START
               }

           }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            MENU_ID_INQUIRY -> {
                viewModel.toast("내 문의")
            }
            MENU_ID_LOGOUT -> {
                Prefs.token = null
                Prefs.refreshToken = null
                viewModel.startActivityAndFinish<SigninActivity>()
            }
        }
        drawerLayout.closeDrawer(navigationView)
        return true
    }

    companion object {
        private const val MENU_ID_INQUIRY = 1
        private const val MENU_ID_LOGOUT = 2
    }


}