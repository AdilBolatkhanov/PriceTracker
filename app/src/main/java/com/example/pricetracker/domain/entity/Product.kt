package com.example.pricetracker.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val category: String
)
