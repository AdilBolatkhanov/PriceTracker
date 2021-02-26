package com.example.pricetracker.ui.products

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pricetracker.R
import com.example.pricetracker.ui.BaseFragment
import com.example.pricetracker.ui.products.adapter.ProductGridItemDecoration
import com.example.pricetracker.ui.products.adapter.StaggeredProductCardAdapter
import com.example.pricetracker.util.NavigationIconClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.product_grid_fragment.*
import kotlinx.android.synthetic.main.product_grid_fragment.view.*

@AndroidEntryPoint
class ProductsGridFragment : BaseFragment(R.layout.product_grid_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupBackground()
        setupRecycler()
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(app_bar)
        app_bar.setNavigationOnClickListener(
            NavigationIconClickListener(
                requireActivity(),
                product_grid,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(requireContext(), R.drawable.shr_branded_menu),
                ContextCompat.getDrawable(requireContext(), R.drawable.shr_close_menu)
            )
        )
    }

    private fun setupBackground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            product_grid.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.shr_product_grid_background_shape
            )
        }
    }

    private fun setupRecycler() {
        recycler_view.apply {
            setHasFixedSize(true)
            val gridLayoutManager = GridLayoutManager(context, 2, RecyclerView.HORIZONTAL, false)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position % 3 == 2) 2 else 1
                }
            }
            adapter = StaggeredProductCardAdapter()
            layoutManager = gridLayoutManager
            val largePadding = resources.getDimensionPixelSize(R.dimen.shr_product_grid_spacing)
            val smallPadding = resources.getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small)
            addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)
    }


}