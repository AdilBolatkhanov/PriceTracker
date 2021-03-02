package com.example.pricetracker.domain.entity

import com.example.pricetracker.ui.detail.model.Image

data class ProductDetail(
    val name: String,
    val prices: List<ShopPrice>,
    val capacity: String,
    val finish: String,
    val width: String,
    val height: String,
    val depth: String,
    val dimensions: String,
    val maxPrice: String,
    val images: List<Image>
)

data class ShopPrice(
    val cost: Int,
    val shopName: String,
    val shopUrl: String
)