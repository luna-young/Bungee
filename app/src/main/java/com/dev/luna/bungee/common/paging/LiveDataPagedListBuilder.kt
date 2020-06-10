package com.dev.luna.bungee.common.paging

import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList


//PageKeyedDataSource를 이용해 LiveData<PagedList>를 쉽게 만들 수 있도록 정의한 인터페이스
interface LiveDataPagedListBuilder<K, T> {

    //이 인터페이스를 구현하는 클래스에서 구현해줘야 할 함수. 구현체에서 필요한 DataSource 클래스를 반환
    fun createDataSource(): DataSource<K, T>

    //DataSource를 생성해줄 DataSource.Factory 객체를 만드는 함수
    private fun factory() = object :
        DataSource.Factory<K, T>() {
        override fun create(): DataSource<K, T> = createDataSource()
    }

    //한 페이지를 어떻게 가져올 것인가에 대한 설정 객체를 만들어주는 함수. 한 페이지를 10개로 처리하도록 설정
    private fun config() = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()

    //최종 목표 => LiveData<PagedList>를 생성해주는 함수
    fun buildPagedList() = LivePagedListBuilder(factory(), config()).build()
}