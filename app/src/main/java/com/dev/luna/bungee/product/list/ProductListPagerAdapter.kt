package com.dev.luna.bungee.product.list

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dev.luna.bungee.product.category.categoryList


//viewpager에서 fragment를 보여줄 수 있도록 FragmentStatePagerAdapter를 상속받는 클래스 구현
//FragmentStatePagerAdapter는 생성자 파라미터로 FragmentManager가 필요---이건 FragmentActivity로부터 가져올 수 있음
class ProductListPagerAdapter(
        fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(
        fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT //현재 fragment만 라이프 사이클의 resumed상태에 있을 수 있음, 나머지는 started상태
) {

    //view pager에서 보여줄 fragment의 리스트
    private val fragments = categoryList.map {
        ProductListFragment.newInstance(it.id, it.name)
    }

    //지정된 위치의 fragment 반환
    override fun getItem(position: Int) = fragments[position]

    //fragment의 수를 반환
    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int) = getItem(position).title
}