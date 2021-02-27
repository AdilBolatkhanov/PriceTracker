package com.example.pricetracker.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pricetracker.domain.entity.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Query("SELECT * FROM product")
    fun getAllProducts(): Flow<List<Product>>

    @Query("DELETE FROM product")
    suspend fun deleteAllNotes()
}