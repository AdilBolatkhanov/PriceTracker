package com.example.pricetracker.ui.products

import androidx.hilt.Assisted
import androidx.lifecycle.*
import com.example.pricetracker.data.dto.products.Category
import com.example.pricetracker.domain.GetAllProductsUseCase
import com.example.pricetracker.domain.entity.Product
import com.example.pricetracker.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class ProductGridViewModel constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _forceUpdate = MutableLiveData<Boolean>(false)

    private val _items = _forceUpdate.switchMap { forceUpdate ->
        getAllProductsUseCase(forceUpdate).asLiveData(viewModelScope.coroutineContext)
    }.distinctUntilChanged().switchMap {
        filterProducts(it)
    }

    val items: LiveData<Result<List<Product>>> = _items

    val empty: LiveData<Boolean> = Transformations.map(_items) { result ->
        if (result is Result.Success)
            result.data.isEmpty()
        else
            true
    }

    init {
        setFiltering(getSavedFilterType())
        setCategory(getSavedCategory())
        loadProducts(true)
    }

    fun setFiltering(type: ProductFilterType){
        savedStateHandle.set(PRODUCTS_FILTER_SAVED_STATE_KEY, type)
        loadProducts(false)
    }

    fun setCategory(type: Category){
        savedStateHandle.set(PRODUCTS_CATEGORY_SAVED_STATE_KEY, type)
        loadProducts(false)
    }

    fun loadProducts(forceUpdate: Boolean) = _forceUpdate.postValue(forceUpdate)

    private fun filterProducts(productRes: Result<List<Product>>): LiveData<Result<List<Product>>> {
        val result = MutableLiveData<Result<List<Product>>>()

        if (productRes is Result.Success){
            viewModelScope.launch {
                result.postValue(filterItems(productRes.data, getSavedFilterType(), getSavedCategory()))
            }
        }else{
            result.postValue(productRes)
        }
        return result;
    }

    private fun filterItems(products: List<Product>, filterType: ProductFilterType, category: Category): Result<List<Product>>{
        var productsToShow = products.filter { product ->
            if (category == Category.ALL)
                true
            else
                product.category == category
        }
        productsToShow = productsToShow.filter { product ->
            when (filterType){
                ProductFilterType.POPULAR -> true
                ProductFilterType.LATEST -> product.id % 2 == 0
                ProductFilterType.OLDEST -> product.id % 2 == 1
            }
        }
        return Result.Success(productsToShow)
    }

    private fun getSavedFilterType(): ProductFilterType {
        return savedStateHandle.get(PRODUCTS_FILTER_SAVED_STATE_KEY) ?: ProductFilterType.LATEST
    }

    private fun getSavedCategory(): Category {
        return savedStateHandle.get(PRODUCTS_CATEGORY_SAVED_STATE_KEY) ?: Category.ALL
    }
}

private const val PRODUCTS_FILTER_SAVED_STATE_KEY = "TASKS_FILTER_SAVED_STATE_KEY"
private const val PRODUCTS_CATEGORY_SAVED_STATE_KEY = "TASKS_FILTER_SAVED_STATE_KEY"
