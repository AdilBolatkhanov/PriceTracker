package com.example.pricetracker.data.dto.products

data class ProductsRemoteItem(
    val category: Category,
    val id: Int,
    val name: String
)

data class Category(
    val id: Int,
    val name: String
)