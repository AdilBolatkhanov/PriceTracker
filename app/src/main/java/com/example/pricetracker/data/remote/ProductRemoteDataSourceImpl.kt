package com.example.pricetracker.data.remote

import com.example.pricetracker.data.dto.mappers.toDomainModel
import com.example.pricetracker.data.remote.api.TrackerApi
import com.example.pricetracker.domain.entity.Product
import com.example.pricetracker.domain.entity.ProductDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.pricetracker.util.Result
import java.lang.Exception

class ProductRemoteDataSourceImpl(
    private val api: TrackerApi
): ProductRemoteDataSource {
    override suspend fun getAllProducts(): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getAllProducts()
            if (response.isSuccessful) {
                 Result.Success(response.body()!!.map { it.toDomainModel() })
            } else {
                Result.Error(response.message())
            }
        }catch (e: Exception){
            Result.Error("Couldn't connect to the servers. Check your connection")
        }
    }

    override suspend fun getDetailedProducts(id: Int): Result<ProductDetail> = withContext(Dispatchers.IO) {
        try {
            val response = api.getDetailOfProduct(id)
            if (response.isSuccessful) {
                Result.Success(response.body()!!.toDomainModel())
            } else {
                Result.Error(response.message())
            }
        }catch (e: Exception){
            Result.Error("Couldn't connect to the servers. Check your connection")
        }
    }
}