package com.example.pricetracker.data.local

import androidx.lifecycle.LiveData
import com.example.pricetracker.domain.entity.Product
import com.example.pricetracker.util.Result

interface ProductLocalDataSource {
    suspend fun insertProducts(products: List<Product>)

    suspend fun deleteAllProducts()

    suspend fun getAllProducts(): Result<List<Product>>

    fun observeProducts(): LiveData<Result<List<Product>>>
}