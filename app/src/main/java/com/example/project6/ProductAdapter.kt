package com.example.project6

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.util.Locale

class ProductAdapter(
    private val context: Context,
    private var products: MutableList<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProductImage: ImageView = itemView.findViewById(R.id.ivProductImage)
        val tvProductTitle: TextView = itemView.findViewById(R.id.tvProductTitle)
        val tvProductPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        val tvProductBrandCategory: TextView = itemView.findViewById(R.id.tvProductBrandCategory)
        val tvProductRating: TextView = itemView.findViewById(R.id.tvProductRating)
        val tvProductDescription: TextView = itemView.findViewById(R.id.tvProductDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]

        holder.tvProductTitle.text = product.title
        holder.tvProductPrice.text = String.format(Locale.US, "$%.2f", product.price)
        holder.tvProductRating.text = String.format(Locale.US, "⭐ %.2f", product.rating)
        holder.tvProductBrandCategory.text = "${product.brand} • ${product.category.replaceFirstChar { it.uppercase() }}"
        holder.tvProductDescription.text = product.description

        Glide.with(context)
            .load(product.thumbnailUrl)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .transform(RoundedCorners(16))
            .into(holder.ivProductImage)

        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
    }

    override fun getItemCount(): Int = products.size

    fun updateProducts(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }
}
