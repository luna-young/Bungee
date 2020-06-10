package com.dev.luna.bungee.product.list

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.luna.bungee.api.ApiGenerator
import com.dev.luna.bungee.api.response.ProductListItemResponse
import com.dev.luna.bungee.common.paging.LiveDataPagedListBuilder
import com.dev.luna.bungee.product.ProductStatus
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.NumberFormat

//PagedListAdapter를 상속받은 클래스
class ProductListPagedAdapter (
        private val listener: OnItemClickListener
) : PagedListAdapter<ProductListItemResponse,
        ProductListPagedAdapter.ProductItemViewHolder>(
        DIFF_CALLBACK //동일한 데이터의 뷰를 다시 그려주는 것을 방지하기 위해 생성자에서 객체의 동일성을 검사하기 위한 콜백을 받음 -> companion object에 정의
) {

    //recycler view가 새 viewholder를 요구했을 때 호출되는 콜백 -> ProductItemViewHolder 클래스의 인스턴스를 생성하여 반환
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ) = ProductItemViewHolder(parent, listener)

    //recycler view가 특정위치의 데이터를 출력해주려 할 때 호출되는 콜백
        //ProductItemViewHolder에서 가지고 있는 itemView에 데이터를 표시해주기 위해
        //ProductItemViewHolder.bind() 함수를 호출
    override fun onBindViewHolder(
            holder: ProductItemViewHolder,
            position: Int
    ) {
            holder.bind(getItem(position))
    }

    //ProductListItemUI를 가지고 있어야 하는 ViewHolder클래스
    //RecyclerView.ViewHolder를 상속받으며, ViewHolder의 생성자에 ProductListItemUI의 인스턴스를 넘겨줌
    //이렇게 넘겨준 뷰는 내부에서 itemView라는 프로퍼티로 사용할 수 있음
    class ProductItemViewHolder (
            parent: ViewGroup,
            private val listener: OnItemClickListener, //클릭리스터를 통해 OnClickProduct() 함수를 호출하다록 함. 이때 productId도 같이 넘김.
            private val ui: ProductListItemUI = ProductListItemUI()
    ) : RecyclerView.ViewHolder (
            ui.createView(AnkoContext.create(parent.context, parent))
    ) {

        var productId: Long? = null

        init {
            itemView.onClick { listener.onClickProduct(productId) }
        }

        //recycler view가 화면에 아이템을 표시해줄 때 어댑터의 onBindViewHolder 콜백에서 호출되도록 만든함수
        //productListItemUI의 이미지와 상품명 그리고 가격을 데이터의 값으로 변경해줌
        fun bind(item: ProductListItemResponse?) = item?.let {
            this.productId = item.id
            val soldOutString =
                    if(ProductStatus.SOLD_OUT == item.status) "품절" else ""
            val commaSeparatedPrice =
                    NumberFormat.getNumberInstance().format(item.price)

            ui.productName.text = item.name
            ui.price.text = "$commaSeparatedPrice $soldOutString"

            Glide.with(ui.imageView)
                    .load("${ApiGenerator.HOST}${item.imagePaths.firstOrNull()}")
                    .centerCrop()
                    .into(ui.imageView)
        }
    }

    companion object {
        val DIFF_CALLBACK =
                object  : DiffUtil.ItemCallback<ProductListItemResponse>() {
                    override fun areItemsTheSame(
                            oldItem: ProductListItemResponse,
                            newItem: ProductListItemResponse
                    ) = oldItem.id == newItem.id //아이템이 동일한지 여부는 id를 통해 검사

                    override fun areContentsTheSame(
                            oldItem: ProductListItemResponse,
                            newItem: ProductListItemResponse
                    ) = oldItem.toString() == newItem.toString() //컨텐츠의 동일성 여부는 data class에 자동생성되는 toString() 함수로 검사
                }
    }

    //상품 클릭 시 동작할 리스너 인터페이스 -> viewmodel이나 activity에서 구현해 사용 가능
    interface OnItemClickListener {
        fun onClickProduct(productId: Long?)
    }

    //LiveDataPagedListBuilder를 구현해 상품 목록에 필요한 LiveData<PagedList<ProductListItemResponse>>를 생성해줄 인터페이스
    interface ProductLiveDataBuilder
        : LiveDataPagedListBuilder<Long, ProductListItemResponse>
}