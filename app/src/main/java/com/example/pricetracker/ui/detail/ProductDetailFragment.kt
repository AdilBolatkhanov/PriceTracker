package com.example.pricetracker.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.pricetracker.R
import com.example.pricetracker.ui.detail.adapte.DynamicStateImagePagerAdapter
import com.example.pricetracker.util.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.product_detail_fragment.*

@AndroidEntryPoint
class ProductDetailFragment : Fragment(R.layout.product_detail_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupViewPager(){
        val adapter = DynamicStateImagePagerAdapter(requireActivity())
        adapter.setImages(Constant.getImages())
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = adapter
        indicator.attachToPager(viewPager)
        viewPager.setCurrentItem(0, false)

    }

}