package com.example.pricetracker.domain

import com.example.pricetracker.domain.entity.ProductDetail
import com.example.pricetracker.domain.repository.TrackerRepository
import com.example.pricetracker.util.Result
import javax.inject.Inject

class GetDetailUseCase @Inject constructor(
    private val repository: TrackerRepository
) {
    suspend operator fun invoke(productId: Int): Result<ProductDetail> {
        return repository.getDetailOfProduct(productId)
    }
}