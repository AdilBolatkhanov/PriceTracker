package com.example.pricetracker.data.remote

import com.example.pricetracker.data.dto.detail.ProductDetailRemoteDTO
import com.example.pricetracker.data.dto.products.ProductsRemoteItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TrackerApi {
    @GET("/api/products")
    suspend fun getAllProducts(): Response<List<ProductsRemoteItem>>

    @GET("/api/products/{id}")
    suspend fun getDetailOfProduct(@Path("id") id: Int): Response<ProductDetailRemoteDTO>
}