package com.example.pricetracker.data.dto.detail

data class ProductDetail(
    val name: String,
    val prices: List<Price>
)

data class Price(
    val cost: Int,
    val shop: Shop
)