package com.example.receitas.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.receitas.R
import com.example.receitas.model.Product
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.row_product.view.*

class ProductAdapter(
    private val products: List<Product>,
    private val context: Context,
    private val listener: (Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_product, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = products[position]

        if (holder is ViewHolder) {
            holder?.let {
                it.bind(product, position, listener)
            }
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun updateList() {
        this.notifyDataSetChanged()
    }

}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(product: Product?, position: Int?, listener: (Product) -> Unit) {
        val rowProductTittle = itemView.rowProductTittle
        val rowProductDescription = itemView.rowProductDescription
        val rowProductCategory = itemView.rowProductCategory
        val rowImageProduct = itemView.rowImageProduct

        product?.let {
            rowProductTittle.text = it.tittle.toString()
            rowProductDescription.text = it.description.toString()
            rowProductCategory.text = it.category.toString()

            val storage = FirebaseStorage.getInstance()
            val gsReference = storage.reference
            gsReference.child("images/" + it.tittle + ".jpg").downloadUrl.addOnSuccessListener {
                Picasso.get()
                    .load(it)
                    .placeholder(R.mipmap.ic_launcher)
                    .transform(CropCircleTransformation())
                    .into(rowImageProduct)
            }
                .addOnFailureListener {
                }

            itemView.setOnClickListener { listener(product) }
        }
    }
}