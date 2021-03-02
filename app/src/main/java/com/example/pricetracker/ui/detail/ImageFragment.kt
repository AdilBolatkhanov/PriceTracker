package com.example.pricetracker.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.pricetracker.R
import kotlinx.android.synthetic.main.image_fragment.*
import kotlinx.android.synthetic.main.product_detail_fragment.*

class ImageFragment: Fragment(R.layout.image_fragment) {
    private var imageId = 0
    private var imageUrl = ""

    companion object {

        private const val EXTRA_IMAGE_ID = "EXTRA_IMAGE_ID"
        private const val EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL"

        fun newInstance(imageId: Int, imageUrl: String): ImageFragment {
            val fragment = ImageFragment()
            val bundle = Bundle()
            bundle.putInt(EXTRA_IMAGE_ID, imageId)
            bundle.putString(EXTRA_IMAGE_URL, imageUrl)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageId = arguments?.getInt(EXTRA_IMAGE_ID, imageId) ?: imageId
        imageUrl = arguments?.getString(EXTRA_IMAGE_URL, imageUrl) ?: imageUrl

        Glide.with(requireContext()).load(imageUrl).into(imageHolder)
    }
}