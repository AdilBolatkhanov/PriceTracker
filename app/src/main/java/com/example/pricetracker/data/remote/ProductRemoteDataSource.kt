package com.example.pricetracker.data.remote

import com.example.pricetracker.domain.entity.Product
import com.example.pricetracker.domain.entity.ProductDetail
import com.example.pricetracker.util.Result

interface ProductRemoteDataSource {

    suspend fun getAllProducts(): Result<List<Product>>

    suspend fun getDetailedProducts(id: Int): Result<ProductDetail>
}