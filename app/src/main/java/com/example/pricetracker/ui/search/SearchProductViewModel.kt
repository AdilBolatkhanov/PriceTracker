package com.example.pricetracker.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.example.pricetracker.domain.SearchByNameUseCase
import com.example.pricetracker.domain.entity.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchProductViewModel @Inject constructor(
    val searchByNameUseCase: SearchByNameUseCase
) : ViewModel() {

    lateinit var searchResult: LiveData<PagedList<Product>>

    init {
        searchResult = searchByNameUseCase("")
    }

    private var debounceJob: Job? = null

    fun searchByName(query: String) {
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(600L)
            searchResult = searchByNameUseCase(query)
        }
    }

}