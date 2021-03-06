package com.example.pricetracker.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.pricetracker.domain.entity.Product
import com.example.pricetracker.domain.entity.ProductDetail
import com.example.pricetracker.util.Result
import kotlinx.coroutines.flow.Flow

interface TrackerRepository {

    fun getAllProducts(forceUpdate: Boolean): Flow<Result<List<Product>>>

    suspend fun getDetailOfProduct(productId: Int): Result<ProductDetail>

    fun searchByName(query: String): LiveData<PagedList<Product>>
}