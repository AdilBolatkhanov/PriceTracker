package com.example.pricetracker.domain

import com.example.pricetracker.domain.entity.Product
import com.example.pricetracker.domain.repository.TrackerRepository
import com.example.pricetracker.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val repository: TrackerRepository
) {
    operator fun invoke(forceUpdate: Boolean): Flow<Result<List<Product>>> =
        repository.getAllProducts(forceUpdate)
}