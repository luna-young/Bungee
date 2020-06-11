package com.dev.luna.bungee.product

import android.app.usage.UsageEvents.Event.NONE
import android.view.Gravity
import android.view.MenuItem
import android.view.MenuItem.SHOW_AS_ACTION_ALWAYS
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import com.dev.luna.bungee.R
import com.dev.luna.bungee.view.borderBottom
import org.jetbrains.anko.*
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.dev.luna.bungee.common.Prefs
import com.dev.luna.bungee.signin.SigninActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.design.navigationView
import org.jetbrains.anko.design.themedTabLayout
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onQueryTextListener
import org.jetbrains.anko.support.v4.drawerLayout
import org.jetbrains.anko.support.v4.viewPager

class ProductMainUI(
        private val viewModel: ProductMainViewModel
) : AnkoComponent<ProductMainActivity> ,
    NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout: DrawerLayout
    lateinit var toolBar : Toolbar
    lateinit var navigationView: NavigationView
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun createView(ui: AnkoContext<ProductMainActivity>) =
           ui.drawerLayout {
               drawerLayout = this

               frameLayout {
                   verticalLayout {
                       toolBar = toolbar {
                           title = "Bungee"
                           bottomPadding = dip(1)
                           background = borderBottom(width = dip(1))
                           menu.add("Search")
                                   .setIcon(R.drawable.ic_search)
                                   .setActionView( searchView {
                                       onQueryTextListener {
                                            onQueryTextSubmit { key ->
                                                viewModel.openSearchActivity(key)
                                                true
                                            }
                                       }
                                   })
                                   .setShowAsAction(SHOW_AS_ACTION_ALWAYS)

                       }.lparams(matchParent, wrapContent)

                       //tabLayout {...} 을 사용할 수도 있지만, 테마를 지정해주면 디자인을 변경할 수 있기 때문에
                       //기본 머터리얼 디자인을 사용하기 위해 테마의 리소스 아이디를 파라미터로 받는 themedTabLayout() 함수를 사용
                       tabLayout = themedTabLayout(
                               R.style.Widget_MaterialComponents_TabLayout
                       ) {
                           bottomPadding = dip(1)
                           tabMode = MODE_SCROLLABLE //탭이 화면 범위를 넘어가게 될 경우 스크롤 or 탭 크기를 화면 사이즈에 맞출지
                           tabGravity = GRAVITY_FILL //탭 영역이 상위 컨테이너를 채우게 할지 or 컨텐츠 크기에 맞춰지게 할지
                           background = borderBottom(width = dip(1))
                           lparams(matchParent, wrapContent)
                       }

                       viewPager = viewPager { //viewPager를 생성하는 함수와 빌더블록
                           id = generateViewId()
                       }.lparams(matchParent, matchParent)

                   }
                   floatingActionButton {
                       imageResource = R.drawable.ic_add
                       onClick { viewModel.openRegistrationActivity() }
                   }.lparams {
                       bottomMargin = dip(20)
                       marginEnd = dip(20)
                       gravity = Gravity.END or Gravity.BOTTOM
                   }

               }

               navigationView = navigationView {
                   ProductMainNavHeader()
                           .createView(AnkoContext.create(context, this))
                           .let(::addHeaderView)

                   menu.apply {
                       //groupId, itemId, order, title
                       add(NONE, MENU_ID_INQUIRY, NONE, "내 문의").apply {
                           setIcon(R.drawable.ic_chat)
                       }
                       add(NONE, MENU_ID_LOGOUT, NONE, "로그아웃").apply {
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