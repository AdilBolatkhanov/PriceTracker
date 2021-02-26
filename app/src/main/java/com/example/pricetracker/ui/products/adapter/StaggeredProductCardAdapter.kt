package com.example.pricetracker.ui.products.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pricetracker.R
import com.example.pricetracker.domain.entity.Product

class StaggeredProductCardAdapter :
    ListAdapter<Product, StaggeredProductCardAdapter.ProductViewHolder>(ProductDiffCallback()) {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var productImage: ImageView = itemView.findViewById(R.id.product_image)
        var productTitle: TextView = itemView.findViewById(R.id.product_title)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var layoutId = R.layout.shr_staggered_product_card_first
        if (viewType == 1)
            layoutId = R.layout.shr_staggered_product_card_second
        else if (viewType == 2)
            layoutId = R.layout.shr_staggered_product_card_third

        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.productTitle.text = product.name
        Glide.with(holder.itemView.context).load(product.imageUrl).into(holder.productImage)
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}