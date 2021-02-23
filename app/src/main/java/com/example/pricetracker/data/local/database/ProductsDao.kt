package com.example.pricetracker.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pricetracker.domain.entity.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Query("SELECT * FROM product")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT * FROM product")
    fun observeProducts(): LiveData<List<Product>>

    @Query("DELETE FROM product")
    suspend fun deleteAllNotes()
}