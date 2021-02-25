package com.example.pricetracker.data

import android.content.Context
import com.example.pricetracker.data.local.ProductLocalDataSource
import com.example.pricetracker.data.remote.ProductRemoteDataSource
import com.example.pricetracker.domain.entity.Product
import com.example.pricetracker.domain.entity.ProductDetail
import com.example.pricetracker.domain.repository.TrackerRepository
import com.example.pricetracker.util.Result
import com.example.pricetracker.util.checkForInternetConnection
import com.example.pricetracker.util.networkBoundResource
import kotlinx.coroutines.flow.Flow

class TrackerRepositoryImpl(
    private val localDataSource: ProductLocalDataSource,
    private val remoteDataSource: ProductRemoteDataSource,
    private val context: Context
) : TrackerRepository {

    override fun getAllProducts(forceUpdate: Boolean): Flow<Result<List<Product>>> {
        return networkBoundResource(
            query = {
                localDataSource.getAllProducts()
            },
            fetch = {
                remoteDataSource.getAllProducts()
            },
            saveFetchResult = { result ->
                if (result is Result.Success) {
                    localDataSource.deleteAllProducts()
                    localDataSource.insertProducts(result.data)
                } else if (result is Result.Error) {
                    throw Exception(result.message)
                }
            },
            shouldFetch = {
                forceUpdate && checkForInternetConnection(context)
            }
        )
    }

    override suspend fun getDetailOfProduct(productId: Int): Result<ProductDetail> {
        return remoteDataSource.getDetailedProducts(productId)
    }

}