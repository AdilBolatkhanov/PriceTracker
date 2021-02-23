package com.example.pricetracker.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.pricetracker.data.local.ProductLocalDataSource
import com.example.pricetracker.data.remote.ProductRemoteDataSource
import com.example.pricetracker.domain.TrackerRepository
import com.example.pricetracker.domain.entity.Product
import com.example.pricetracker.domain.entity.ProductDetail
import com.example.pricetracker.util.Result
import java.lang.Exception
import javax.inject.Inject

class TrackerRepositoryImpl @Inject constructor(
    private val localDataSource: ProductLocalDataSource,
    private val remoteDataSource: ProductRemoteDataSource
) : TrackerRepository {
    override suspend fun getAllProducts(forceUpdate: Boolean): Result<List<Product>> {
        if (forceUpdate){
            try {
                updateTasksFromRemoteDataSource()
            }catch (e: Exception){
                return Result.Error(e.message ?: "Something went wrong")
            }
        }
        return localDataSource.getAllProducts()
    }

    override suspend fun getDetailOfProduct(productId: Int): Result<ProductDetail> {
        return remoteDataSource.getDetailedProducts(productId)
    }

    private suspend fun updateTasksFromRemoteDataSource() {
        val remoteTasks = remoteDataSource.getAllProducts()

        if (remoteTasks is Result.Success) {
            localDataSource.deleteAllProducts()
            localDataSource.insertProducts(remoteTasks.data)
        } else if (remoteTasks is Result.Error) {
            throw Exception(remoteTasks.message)
        }
    }

}