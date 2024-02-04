// ProductAdapter.kt
package com.example.maps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import android.content.Intent

class AdaptadorProducto(private val context: Context, private val products: List<Producto>) : BaseAdapter() {

    override fun getCount(): Int {
        return products.size
    }

    override fun getItem(position: Int): Any {
        return products[position]
    }

    override fun getItemId(position: Int): Long {
        return products[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val gridView: View

        if (convertView == null) {
            gridView = inflater.inflate(R.layout.grid_item, null)
            val productName: TextView = gridView.findViewById(R.id.productName)
            val productPrice: TextView = gridView.findViewById(R.id.productPrice)
            val productImage: ImageView = gridView.findViewById(R.id.productImage)
            val buttonVerMas: Button = gridView.findViewById(R.id.button)

            val product = products[position]

            productName.text = product.name
            productPrice.text = product.price
            productImage.setImageResource(product.imageResId)

            buttonVerMas.setOnClickListener {
                val intent = Intent(context, DetallesProductos::class.java)
                intent.putExtra("product", product)
                context.startActivity(intent)
            }
        } else {
            gridView = convertView
        }

        return gridView
    }
}
