package com.example.pricetracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pricetracker.domain.entity.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
abstract class ProductsDatabase: RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}