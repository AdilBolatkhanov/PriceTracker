package com.example.pricetracker.data.local.database

import androidx.room.TypeConverter
import com.example.pricetracker.data.dto.products.Category

class Converters {

    @TypeConverter
    fun fromCategory(value: Category) = value.name

    @TypeConverter
    fun toCategory(value: String) = enumValueOf<Category>(value)
}