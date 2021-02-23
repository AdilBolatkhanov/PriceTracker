package com.example.pricetracker.data.dto.mappers

import com.example.pricetracker.data.dto.detail.PriceRemote
import com.example.pricetracker.data.dto.detail.ProductDetailRemoteDTO
import com.example.pricetracker.data.dto.products.ProductsRemoteItem
import com.example.pricetracker.domain.entity.Product
import com.example.pricetracker.domain.entity.ProductDetail
import com.example.pricetracker.domain.entity.ShopPrice

fun ProductsRemoteItem.toDomainModel(): Product {
    return Product(
        id = id,
        name = name,
        category = category.name
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

fun PriceRemote.toPriceDomainModel(): ShopPrice{
    return ShopPrice(
        cost = cost,
        shopName = shop.name,
        shopUrl = ""
    )
}