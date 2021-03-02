package com.example.pricetracker.ui.detail.adapte

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pricetracker.ui.detail.ImageFragment
import com.example.pricetracker.ui.detail.model.Image

class DynamicStateImagePagerAdapter(activity: FragmentActivity)
    : FragmentStateAdapter(activity) {

    private var categoryFragmentHolders = listOf<ImageHolder>()

    override fun createFragment(position: Int): Fragment =
        categoryFragmentHolders[position].fragment

    override fun containsItem(itemId: Long): Boolean =
        categoryFragmentHolders.firstOrNull { it.image.id.toLong() == itemId } != null

    override fun getItemId(position: Int): Long =
        categoryFragmentHolders[position].image.id.toLong()

    override fun getItemCount(): Int =
        categoryFragmentHolders.size

    fun setImages(categories: List<Image>) {
        if (categories != categoryFragmentHolders.map { it.image }) {
            categoryFragmentHolders = categories.map {
                val fragment = ImageFragment.newInstance(it.id, it.imageUrl)
                ImageHolder(fragment, it)
            }
            notifyDataSetChanged()
        }
    }

    private data class ImageHolder(val fragment: Fragment, val image: Image)
}