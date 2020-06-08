package com.dev.luna.bungee.product.category

data class Category (
        val id: Int,
        val name: String)

    val categoryList = listOf(
        Category(0, "여성의류"),
        Category(1, "남성의류"),
        Category(2, "아동복"),
        Category(3, "잡화"),
        Category(4, "가전제품"),
        Category(5, "생활용품"),
        Category(6, "도서"),
        Category(7, "반려동물용품"),
        Category(8, "식품"),
        Category(9, "디지털기기"),
        Category(10, "문구류")
    )
