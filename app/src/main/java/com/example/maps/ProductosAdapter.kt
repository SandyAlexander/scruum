package com.example.maps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProductosAdapter (private val productoList:MutableList<ProductoCarrito>) : RecyclerView.Adapter<ProductosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductosViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ProductosViewHolder(layoutInflater.inflate(R.layout.item_producto, parent, false), this)
    }

    override fun getItemCount(): Int = productoList.size

    override fun onBindViewHolder(holder: ProductosViewHolder, position: Int) {
        val item = productoList[position]
        holder.render(item)
    }
}