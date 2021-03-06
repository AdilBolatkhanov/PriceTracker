package com.example.pricetracker.ui.products

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pricetracker.R
import com.example.pricetracker.data.dto.products.Category
import com.example.pricetracker.domain.entity.Product
import com.example.pricetracker.ui.BaseFragment
import com.example.pricetracker.ui.products.adapter.ProductGridItemDecoration
import com.example.pricetracker.ui.products.adapter.StaggeredProductCardAdapter
import com.example.pricetracker.util.EventObserver
import com.example.pricetracker.util.NavigationIconClickListener
import com.example.pricetracker.util.Result
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.backdrop.*
import kotlinx.android.synthetic.main.product_grid_fragment.*
import timber.log.Timber

@AndroidEntryPoint
class ProductsGridFragment : BaseFragment(R.layout.product_grid_fragment) {

    private val viewModel: ProductGridViewModel by viewModels()

    private lateinit var navIconClickListener: NavigationIconClickListener

    private lateinit var productGridAdapter: StaggeredProductCardAdapter
    private var checkedItem = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupBackground()
        setupRecycler()
        subscribeToObservers()
        setupSwipeRefresh()
        setClickListeners()
    }

    private fun setClickListeners() {
        productGridAdapter.setClickListener {
            findNavController().navigate(
                ProductsGridFragmentDirections.actionProductsGridFragmentToProductDetailFragment(it.id)
            )
        }

        featuredBtn.setOnClickListener {
            viewModel.setCategory(Category.ALL)
            clickOnNavIcon()
        }
        smartphoneBtn.setOnClickListener {
            viewModel.setCategory(Category.SMARTPHONES)
            clickOnNavIcon()
        }
        laptopBtn.setOnClickListener {
            viewModel.setCategory(Category.LAPTOPS)
            clickOnNavIcon()
        }
        tvBtn.setOnClickListener {
            viewModel.setCategory(Category.TV)
            clickOnNavIcon()
        }
        cameraBtn.setOnClickListener {
            viewModel.setCategory(Category.CAMERA)
            clickOnNavIcon()
        }
        appliancesBtn.setOnClickListener {
            viewModel.setCategory(Category.APPLIANCES)
            clickOnNavIcon()
        }
        beautyBtn.setOnClickListener {
            viewModel.setCategory(Category.BEAUTY)
            clickOnNavIcon()
        }
    }

    private fun clickOnNavIcon() {
        navIconClickListener.apply {
            onClick(navView)
        }
    }

    private fun subscribeToObservers() {
        viewModel.items.observe(viewLifecycleOwner, ::handleGetAllProducts)
        viewModel.searchEvent.observe(viewLifecycleOwner, EventObserver{
            findNavController().navigate(
                ProductsGridFragmentDirections.actionProductsGridFragmentToSearchProductFragment()
            )
        })
    }

    private fun handleGetAllProducts(result: Result<List<Product>>) {
        when (result) {
            is Result.Success -> {
                Timber.d(result.data.toString())
                swipeRefreshLayout.isRefreshing = false
                productGridAdapter.submitList(result.data)
            }
            is Result.Error -> {
                showSnackbar(result.message)
                swipeRefreshLayout.isRefreshing = false
            }
            is Result.Loading -> {
                swipeRefreshLayout.isRefreshing = true
            }
        }
    }

    private fun setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadProducts(true)
        }
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(app_bar)
        navIconClickListener = NavigationIconClickListener(
            requireActivity(),
            product_grid,
            AccelerateDecelerateInterpolator(),
            ContextCompat.getDrawable(requireContext(), R.drawable.shr_branded_menu),
            ContextCompat.getDrawable(requireContext(), R.drawable.shr_close_menu)
        )
        app_bar.setNavigationOnClickListener(navIconClickListener)
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
            productGridAdapter = StaggeredProductCardAdapter()
            adapter = productGridAdapter
            layoutManager = gridLayoutManager
            val largePadding = resources.getDimensionPixelSize(R.dimen.shr_product_grid_spacing)
            val smallPadding =
                resources.getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small)
            addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                viewModel.searchProduct()
            }
            R.id.filter -> showFilterDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showFilterDialog() {
        val singleItems = arrayOf(
            resources.getString(R.string.filter_popular),
            resources.getString(R.string.filter_latest),
            resources.getString(R.string.filter_oldest)
        )
        val title = SpannableString(resources.getString(R.string.filter_dialog))
        title.setSpan(StyleSpan(Typeface.BOLD),0,title.length,0)
        title.setSpan(RelativeSizeSpan(1.1F),0,title.length,0)
         MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_App_MaterialAlertDialog)
            .setTitle(title)
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                viewModel.setFiltering(
                    when (which) {
                        1 -> ProductFilterType.LATEST
                        2 -> ProductFilterType.OLDEST
                        else -> ProductFilterType.POPULAR
                    }
                )
                checkedItem = which
                dialog.dismiss()
            }
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)
    }


}