package com.example.pricetracker.ui.products

import androidx.hilt.Assisted
import androidx.lifecycle.*
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

    fun loadProducts(forceUpdate: Boolean) = _forceUpdate.postValue(forceUpdate)

    private fun filterProducts(productRes: Result<List<Product>>): LiveData<Result<List<Product>>> {
        val result = MutableLiveData<Result<List<Product>>>()

        if (productRes is Result.Success){
            viewModelScope.launch {
                result.postValue(filterItems(productRes.data, getSavedFilterType()))
            }
        }else{
            result.postValue(productRes)
        }
        return result;
    }

    private fun filterItems(products: List<Product>, filterType: ProductFilterType): Result<List<Product>>{
        val productsToShow = ArrayList<Product>()
        for (product in products){

        }
        return Result.Success(productsToShow)
    }

    private fun getSavedFilterType(): ProductFilterType {
        return savedStateHandle.get(PRODUCTS_FILTER_SAVED_STATE_KEY) ?: ProductFilterType.LATEST
    }
}

private const val PRODUCTS_FILTER_SAVED_STATE_KEY = "TASKS_FILTER_SAVED_STATE_KEY"
private const val PRODUCTS_CATEGORY_SAVED_STATE_KEY = "TASKS_FILTER_SAVED_STATE_KEY"
