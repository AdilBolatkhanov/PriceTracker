package com.example.pricetracker.data.local.database

import androidx.paging.DataSource
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

    @Query("SELECT * FROM product WHERE name LIKE '%' || :query || '%'")
    fun searchByName(query: String): DataSource.Factory<Int, Product>

    @Query("DELETE FROM product")
    suspend fun deleteAllNotes()
}