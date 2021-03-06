package com.example.pricetracker.domain

import com.example.pricetracker.domain.repository.TrackerRepository
import javax.inject.Inject

class SearchByNameUseCase @Inject constructor(
    private val repository: TrackerRepository
) {
    operator fun invoke(query: String) = repository.searchByName(query)
}