package com.dev.luna.bungee.product.category

data class Category (
        val id: Int,
        val name: String)

    val categoryList = listOf(
        Category(0, "상의"),
        Category(1, "하의"),
        Category(2, "아우터"),
        Category(3, "가방"),
        Category(5, "신발"),
        Category(6, "액세서리")
    )
