package com.example.pricetracker.data.dto.detail

data class ProductDetailRemoteDTO(
    val name: String,
    val prices: List<PriceRemote>
)

data class PriceRemote(
    val cost: Int,
    val shop: Shop
)

data class Shop(
    val name: String
)