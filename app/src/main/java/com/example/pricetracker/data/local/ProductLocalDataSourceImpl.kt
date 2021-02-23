package com.example.pricetracker.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.pricetracker.data.local.database.ProductsDao
import com.example.pricetracker.domain.entity.Product
import com.example.pricetracker.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductLocalDataSourceImpl(
    private val productsDao: ProductsDao
) : ProductLocalDataSource {

    override suspend fun insertProducts(products: List<Product>) = withContext(Dispatchers.IO) {
        products.forEach { product ->
            productsDao.insertProduct(product)
        }
    }

    override fun observeProducts(): LiveData<Result<List<Product>>> {
        return productsDao.observeProducts().map {
            Result.Success(it)
        }
    }

    override suspend fun deleteAllProducts() = withContext(Dispatchers.IO) {
        productsDao.deleteAllNotes()
    }

    override suspend fun getAllProducts(): Result<List<Product>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(productsDao.getAllProducts())
        } catch (e: Exception) {
            Result.Error(e.message ?: "Something went wrong")
        }
    }
}