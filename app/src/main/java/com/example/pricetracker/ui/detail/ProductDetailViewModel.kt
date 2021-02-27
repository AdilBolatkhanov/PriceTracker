package com.example.pricetracker.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricetracker.domain.GetDetailUseCase
import com.example.pricetracker.domain.entity.ProductDetail
import com.example.pricetracker.util.Event
import com.example.pricetracker.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getDetailUseCase: GetDetailUseCase
) : ViewModel() {

    private val _productDetail = MutableLiveData<Event<Result<ProductDetail>>>()

    val productDetail: LiveData<Event<Result<ProductDetail>>> = _productDetail

    fun getDetail(productId: Int) {
        viewModelScope.launch {
            _productDetail.postValue(Event(getDetailUseCase(productId)))
        }
    }

}