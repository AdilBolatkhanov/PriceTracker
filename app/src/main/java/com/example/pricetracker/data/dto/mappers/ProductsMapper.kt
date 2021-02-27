package com.example.pricetracker.data.dto.mappers

import com.example.pricetracker.data.dto.detail.PriceRemote
import com.example.pricetracker.data.dto.detail.ProductDetailRemoteDTO
import com.example.pricetracker.data.dto.products.ProductsRemoteItem
import com.example.pricetracker.domain.entity.Product
import com.example.pricetracker.domain.entity.ProductDetail
import com.example.pricetracker.domain.entity.ShopPrice
import com.example.pricetracker.util.Constant.IMAGE_STORAGE

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
    return ProductDetail(
        name = name,
        prices = prices.map {
            it.toPriceDomainModel()
        }
    )
}

fun PriceRemote.toPriceDomainModel(): ShopPrice {
    return ShopPrice(
        cost = cost,
        shopName = shop.name,
        shopUrl = ""
    )
}