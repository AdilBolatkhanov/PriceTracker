package com.example.pricetracker.data.dto.mappers

import com.example.pricetracker.data.dto.detail.PriceRemote
import com.example.pricetracker.data.dto.detail.ProductDetailRemoteDTO
import com.example.pricetracker.data.dto.products.ProductsRemoteItem
import com.example.pricetracker.domain.entity.Product
import com.example.pricetracker.domain.entity.ProductDetail
import com.example.pricetracker.domain.entity.ShopPrice
import com.example.pricetracker.util.Constant.IMAGE_STORAGE
import com.example.pricetracker.util.Constant.TYPES_FINISH
import kotlin.math.cos

fun ProductsRemoteItem.toDomainModel(): Product {
    val randomInd = (0 until IMAGE_STORAGE.size).random()
    val imageUrl = IMAGE_STORAGE[randomInd]
    return Product(
        id = id,
        name = name,
        category = category.name,
        imageUrl = imageUrl!!
    )
}

fun ProductDetailRemoteDTO.toDomainModel(): ProductDetail {
    val minPrice = prices[0].cost
    return ProductDetail(
        name = name,
        prices = prices.map {
            it.toPriceDomainModel()
        },
        capacity = "${(32 until 200).random()} GB",
        finish = (TYPES_FINISH[(TYPES_FINISH.indices).random()]),
        width = "${(30 until 120).random()} mm",
        height = "${(100 until 300).random()} mm",
        depth = "${(1 until 15).random()} mm",
        dimensions = "${(1000 until 2000).random()}",
        maxPrice = "$minPrice - ${(minPrice until minPrice + 100000).random()}"
    )
}

fun PriceRemote.toPriceDomainModel(): ShopPrice {
    return ShopPrice(
        cost = cost,
        shopName = shop.name,
        shopUrl = ""
    )
}