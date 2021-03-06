package com.example.pricetracker.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pricetracker.R
import com.example.pricetracker.domain.entity.Product
import com.example.pricetracker.ui.BaseFragment
import com.example.pricetracker.ui.products.ProductsGridFragmentDirections
import com.example.pricetracker.ui.search.adapter.SearchPagedAdapter
import com.example.pricetracker.ui.search.adapter.SearchProductClickListener
import com.example.pricetracker.util.debounce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search_product_fragment.*
import kotlinx.coroutines.*

@AndroidEntryPoint
class SearchProductFragment: BaseFragment(R.layout.search_product_fragment), SearchProductClickListener {

    private val viewModel: SearchProductViewModel by viewModels()

    private val searchAdapter = SearchPagedAdapter(this)

    private var debounceJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        subscribeToObservers()
        setupListeners()
    }

    private fun setupListeners(){

        searchField.doAfterTextChanged { editable ->
            viewModel.searchByName(editable.toString())
            debounceJob?.cancel()
            debounceJob = lifecycleScope.launch {
                delay(610L)
                subscribeToObservers()
            }
        }

        backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun subscribeToObservers(){
        viewModel.searchResult.observe(viewLifecycleOwner) {
            searchAdapter.submitList(it)
        }
    }

    private fun setupRecycler(){
        searchResult.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = searchAdapter
        }
    }

    override fun onClick(product: Product) {
        findNavController().navigate(
            SearchProductFragmentDirections.actionSearchProductFragmentToProductDetailFragment(product.id)
        )
    }

    private fun EditText.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}