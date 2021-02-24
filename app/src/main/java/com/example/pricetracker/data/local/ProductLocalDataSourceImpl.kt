package com.example.pricetracker.data.local

import com.example.pricetracker.data.local.database.ProductsDao
import com.example.pricetracker.domain.entity.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ProductLocalDataSourceImpl(
    private val productsDao: ProductsDao
) : ProductLocalDataSource {

    override suspend fun insertProducts(products: List<Product>) = withContext(Dispatchers.IO) {
        products.forEach { product ->
            productsDao.insertProduct(product)
        }
    }

    override suspend fun deleteAllProducts() = withContext(Dispatchers.IO) {
        productsDao.deleteAllNotes()
    }

    override fun getAllProducts(): Flow<List<Product>> = productsDao.getAllProducts()

}