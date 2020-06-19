package com.dev.luna.bungee.product.detail

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.dev.luna.bungee.api.ApiGenerator
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.imageView

class ImageSliderAdapter: PagerAdapter() {

    var imageUrls : List<String> = listOf()

    //뷰페이저에서 현재 위치한 페이지가 instantiateItem()으로부터 반환된 뷰인지를 비교하는 함수
    //pager adapter가 정상적으로 동작하기 위해서 꼭 구현해주어야 함
    override fun isViewFromObject(view: View, obj: Any) =
            view == obj

    override fun getCount(): Int = imageUrls.size

    //실질적으로 우리가 원하는 뷰를 만들어서 반환
    //원하는 뷰가 이미지 한 개짜리이므로 별도 UI클래스를 만드는 대신 함수 안에 바로 작성
    override fun instantiateItem(
            container: ViewGroup,
            position: Int): Any {

        val view = AnkoContext.create(container.context, container)
                .imageView().apply {
                   Glide.with(this)
                           .load("${ApiGenerator.HOST}${imageUrls[position]}")
                           .into(this)
                }
        container.addView(view)
        return view
    }
    //빈 리스트를 api로부터 받아온 이미지로 교체해주기 위한 함수
    fun updateItems(items: MutableList<String>) {
        imageUrls = items
        notifyDataSetChanged() //이 함수를 호출해줘야 아이템들이 뷰에 정상적으로 반영되게 됨
    }

    //뷰페이저는 현제 페이지 좌우에 이웃한 페이지만 생성하고 이외에는 페이지를 삭제하기 때문에
    //이때 뷰를 제거해주는 것은 개발자의 몫
    override fun destroyItem(
            container: ViewGroup,
            position: Int,
            obj: Any) {
        container.invalidate() //컨테이너에 추가했던 뷰를 제거해줌
    }


}