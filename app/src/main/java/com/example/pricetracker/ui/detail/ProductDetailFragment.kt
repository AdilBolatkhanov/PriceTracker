package com.example.pricetracker.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.pricetracker.R
import com.example.pricetracker.domain.entity.ProductDetail
import com.example.pricetracker.ui.BaseFragment
import com.example.pricetracker.ui.detail.adapte.DynamicStateImagePagerAdapter
import com.example.pricetracker.util.Constant.BELYIVETER
import com.example.pricetracker.util.Constant.MECHTA
import com.example.pricetracker.util.Constant.SULPAK
import com.example.pricetracker.util.Constant.TECHNODOM
import com.example.pricetracker.util.Event
import com.example.pricetracker.util.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.product_detail_fragment.*

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment(R.layout.product_detail_fragment) {

    private val viewModel: ProductDetailViewModel by viewModels()

    private lateinit var adapter: DynamicStateImagePagerAdapter

    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupClickListener()
        subscribeToObservers()
        viewModel.getDetail(args.id)
    }

    private fun subscribeToObservers() {
        viewModel.productDetail.observe(viewLifecycleOwner, ::handleProductDetail)
        viewModel.openLinkInBrowser.observe(viewLifecycleOwner, ::handleOpenLinkInBrowser)
    }

    private fun handleOpenLinkInBrowser(event: Event<String>){
        val webpage: Uri = Uri.parse(event.getContentIfNotHandled())
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun handleProductDetail(detail: Event<Result<ProductDetail>>) {
        detail.peekContent().let { result ->
            when (result) {
                is Result.Success -> {
                    fillLayout(result.data)
                    group.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    group.visibility = View.GONE
                    errorIcon.visibility = View.VISIBLE
                    showSnackbar(result.message)
                }
                is Result.Loading -> group.visibility = View.GONE
            }
        }
    }

    private fun fillLayout(detail: ProductDetail) {
        nameTv.text = detail.name
        capacityValTv.text = detail.capacity
        finishValTv.text = detail.finish
        widthValTv.text = detail.width
        heightValTv.text = detail.height
        depthValTv.text = detail.depth
        dimensionValTv.text = detail.dimensions
        priceValTv.text = detail.maxPrice

        detail.prices.forEach { price ->
            when (price.shopName) {
                SULPAK -> sulpakValTv.text = "${price.cost}"
                TECHNODOM -> technoValTv.text = "${price.cost}"
                MECHTA -> mechtaValTv.text = "${price.cost}"
                BELYIVETER -> belyiValTv.text = "${price.cost}"
            }
        }

        adapter.setImages(detail.images)
        indicator.attachToPager(viewPager)
        viewPager.setCurrentItem(0, false)
    }

    private fun setupClickListener() {
        setupClickListener.setOnClickListener {
            findNavController().popBackStack()
        }
        scrollView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY <= oldScrollY)
                visitWebsiteFab.extend()
            else
                visitWebsiteFab.shrink()
        }
        visitWebsiteFab.setOnClickListener { viewModel.openLinkInBrowser() }
    }

    private fun setupViewPager() {
        adapter = DynamicStateImagePagerAdapter(requireActivity())
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = adapter
    }

}