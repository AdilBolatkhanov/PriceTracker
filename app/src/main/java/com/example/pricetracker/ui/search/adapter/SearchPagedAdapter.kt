package com.example.pricetracker.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pricetracker.R
import com.example.pricetracker.domain.entity.Product
import com.example.pricetracker.ui.products.adapter.ProductDiffCallback
import kotlinx.android.synthetic.main.search_item.view.*

class SearchPagedAdapter(
    private val clickListener: SearchProductClickListener
) : PagedListAdapter<Product, SearchPagedAdapter.ViewHolder>(ProductDiffCallback()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { product ->
            Glide.with(holder.itemView.context)
                .load(product.imageUrl)
                .into(holder.itemView.product_image)
            holder.itemView.title_product.text = product.name
            holder.itemView.setOnClickListener {
                clickListener.onClick(product)
            }
        }
    }

}

interface SearchProductClickListener {
    fun onClick(product: Product)
}