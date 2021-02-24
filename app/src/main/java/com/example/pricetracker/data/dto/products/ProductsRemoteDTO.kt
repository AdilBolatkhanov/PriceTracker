package com.example.pricetracker.data.dto.products

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductsRemoteItem(
    val category: CategoryRemote,
    val id: Int,
    val name: String
)

data class CategoryRemote(
    val id: Int,
    val name: Category
)

enum class Category(val value: String){
    @SerializedName("Smartphones")
    SMARTPHONES("Smartphones"),

    @SerializedName("Laptops")
    LAPTOPS("Laptops"),

    @SerializedName("TV")
    TV("TV"),

    @SerializedName("Camera")
    CAMERA("Camera"),

    @SerializedName("Appliances")
    APPLIANCES("Appliances"),

    @SerializedName("Beauty")
    BEAUTY("Beauty"),

    @Expose(deserialize = false, serialize = false)
    ALL("All")
}