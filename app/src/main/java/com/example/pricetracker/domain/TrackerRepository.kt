package com.example.pricetracker.domain

import androidx.lifecycle.LiveData
import com.example.pricetracker.domain.entity.Product
import com.example.pricetracker.domain.entity.ProductDetail
import com.example.pricetracker.util.Result

interface TrackerRepository {

    suspend fun getAllProducts(forceUpdate: Boolean): Result<List<Product>>

    suspend fun getDetailOfProduct(productId: Int): Result<ProductDetail>
}