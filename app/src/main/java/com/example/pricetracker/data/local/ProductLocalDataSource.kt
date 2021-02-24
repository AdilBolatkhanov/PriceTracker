package com.example.pricetracker.data.local

import com.example.pricetracker.domain.entity.Product
import kotlinx.coroutines.flow.Flow

interface ProductLocalDataSource {
    suspend fun insertProducts(products: List<Product>)

    suspend fun deleteAllProducts()

    fun getAllProducts(): Flow<List<Product>>
}