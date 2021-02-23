package com.example.pricetracker.domain.entity

data class ProductDetail(
    val name: String,
    val prices: List<ShopPrice>
)

data class ShopPrice(
    val cost: Int,
    val shopName: String,
    val shopUrl: String
)