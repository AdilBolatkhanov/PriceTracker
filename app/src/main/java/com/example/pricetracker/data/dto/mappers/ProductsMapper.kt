package com.example.pricetracker.data.dto.mappers

import com.example.pricetracker.data.dto.detail.PriceRemote
import com.example.pricetracker.data.dto.detail.ProductDetailRemoteDTO
import com.example.pricetracker.data.dto.detail.Shop
import com.example.pricetracker.data.dto.products.ProductsRemoteItem
import com.example.pricetracker.domain.entity.Product
import com.example.pricetracker.domain.entity.ProductDetail
import com.example.pricetracker.domain.entity.ShopPrice
import com.example.pricetracker.util.Constant
import com.example.pricetracker.util.Constant.BELYIVETER
import com.example.pricetracker.util.Constant.IMAGE_STORAGE
import com.example.pricetracker.util.Constant.MECHTA
import com.example.pricetracker.util.Constant.SULPAK
import com.example.pricetracker.util.Constant.TECHNODOM
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
    val maxPrice = (minPrice until minPrice + 100000).random()
    val pricesMut = mutableListOf<PriceRemote>()
    pricesMut.add(prices[0])
    when(prices[0].shop.name){
        BELYIVETER -> {
            pricesMut.add(PriceRemote((minPrice until maxPrice).random(), Shop(SULPAK)))
            pricesMut.add(PriceRemote((minPrice until maxPrice).random(), Shop(MECHTA)))
            pricesMut.add(PriceRemote((minPrice until maxPrice).random(), Shop(TECHNODOM)))
        }
        MECHTA -> {
            pricesMut.add(PriceRemote((minPrice until maxPrice).random(), Shop(SULPAK)))
            pricesMut.add(PriceRemote((minPrice until maxPrice).random(), Shop(BELYIVETER)))
            pricesMut.add(PriceRemote((minPrice until maxPrice).random(), Shop(TECHNODOM)))
        }
        SULPAK -> {
            pricesMut.add(PriceRemote((minPrice until maxPrice).random(), Shop(MECHTA)))
            pricesMut.add(PriceRemote((minPrice until maxPrice).random(), Shop(BELYIVETER)))
            pricesMut.add(PriceRemote((minPrice until maxPrice).random(), Shop(TECHNODOM)))
        }
        TECHNODOM -> {
            pricesMut.add(PriceRemote((minPrice until maxPrice).random(), Shop(SULPAK)))
            pricesMut.add(PriceRemote((minPrice until maxPrice).random(), Shop(BELYIVETER)))
            pricesMut.add(PriceRemote((minPrice until maxPrice).random(), Shop(MECHTA)))
        }
    }
    return ProductDetail(
        name = name,
        prices = pricesMut.map {
            it.toPriceDomainModel()
        },
        capacity = "${(32 until 200).random()} GB",
        finish = (TYPES_FINISH[(TYPES_FINISH.indices).random()]),
        width = "${(30 until 120).random()} mm",
        height = "${(100 until 300).random()} mm",
        depth = "${(1 until 15).random()} mm",
        dimensions = "${(1000 until 2000).random()}",
        maxPrice = "$minPrice - $maxPrice",
        images = Constant.getImages()
    )
}

fun PriceRemote.toPriceDomainModel(): ShopPrice {
    return ShopPrice(
        cost = cost,
        shopName = shop.name,
        shopUrl = ""
    )
}