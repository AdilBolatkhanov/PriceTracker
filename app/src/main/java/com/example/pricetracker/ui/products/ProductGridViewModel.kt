package com.example.pricetracker.ui.products

import androidx.hilt.Assisted
import androidx.lifecycle.*
import com.example.pricetracker.data.dto.products.Category
import com.example.pricetracker.domain.GetAllProductsUseCase
import com.example.pricetracker.domain.entity.Product
import com.example.pricetracker.util.Event
import com.example.pricetracker.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductGridViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    @Assisted private val filterStateHandle: SavedStateHandle
) : ViewModel() {

    private val _forceUpdate = MutableLiveData<Boolean>(false)

    private val _items = _forceUpdate.switchMap { forceUpdate ->
        getAllProductsUseCase(forceUpdate).asLiveData(viewModelScope.coroutineContext)
    }.distinctUntilChanged().switchMap {
        filterProducts(it)
    }

    private val _searchEvent = MutableLiveData<Event<Unit>>()
    val searchEvent: LiveData<Event<Unit>> = _searchEvent

    private var currentCategory = Category.ALL

    val items: LiveData<Result<List<Product>>> = _items

    val empty: LiveData<Boolean> = Transformations.map(_items) { result ->
        if (result is Result.Success)
            result.data.isEmpty()
        else
            true
    }

    init {
        setFiltering(getSavedFilterType())
        loadProducts(true)
    }

    fun setFiltering(type: ProductFilterType) {
        filterStateHandle.set(PRODUCTS_FILTER_SAVED_STATE_KEY, type)
        loadProducts(false)
    }

    fun setCategory(type: Category) {
        currentCategory = type
        loadProducts(false)
    }

    fun searchProduct(){
        _searchEvent.value = Event(Unit)
    }

    fun loadProducts(forceUpdate: Boolean) = _forceUpdate.postValue(forceUpdate)

    private fun filterProducts(productRes: Result<List<Product>>): LiveData<Result<List<Product>>> {
        val result = MutableLiveData<Result<List<Product>>>()

        if (productRes is Result.Success) {
            result.postValue(Result.Loading)
            viewModelScope.launch {
                result.postValue(
                    filterItems(
                        productRes.data,
                        getSavedFilterType(),
                        currentCategory
                    )
                )
            }
        } else {
            result.postValue(productRes)
        }
        return result
    }

    private fun filterItems(
        products: List<Product>,
        filterType: ProductFilterType,
        category: Category
    ): Result<List<Product>> {
        var productsToShow = products.filter { product ->
            if (category == Category.ALL)
                true
            else
                product.category == category
        }
        productsToShow = when (filterType) {
            ProductFilterType.POPULAR -> productsToShow.sortedBy { it.imageUrl }
            ProductFilterType.LATEST -> productsToShow.sortedBy { it.id }
            ProductFilterType.OLDEST -> productsToShow.sortedByDescending { it.id }
        }
        return Result.Success(productsToShow)
    }

    private fun getSavedFilterType(): ProductFilterType {
        return filterStateHandle.get(PRODUCTS_FILTER_SAVED_STATE_KEY) ?: ProductFilterType.LATEST
    }

}

private const val PRODUCTS_FILTER_SAVED_STATE_KEY = "TASKS_FILTER_SAVED_STATE_KEY"
private const val PRODUCTS_CATEGORY_SAVED_STATE_KEY = "TASKS_FILTER_SAVED_STATE_KEY"
