package com.example.pricetracker.domain.entity

data class ProductDetail(
    val name: String,
    val prices: List<ShopPrice>,
    val capacity: String,
    val finish: String,
    val width: String,
    val height: String,
    val depth: String,
    val dimensions: String,
    val maxPrice: String
)

data class ShopPrice(
    val cost: Int,
    val shopName: String,
    val shopUrl: String
)