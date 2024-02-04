package com.example.maps

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductosViewHolder(view: View, val adapter: RecyclerView.Adapter<*>):RecyclerView.ViewHolder(view) {

    val namepro = view.findViewById<TextView>(R.id.txtnombre)
    val cantpro = view.findViewById<TextView>(R.id.txtcantidad)
    val preciopro = view.findViewById<TextView>(R.id.txtprecio)
    val idproducto = view.findViewById<TextView>(R.id.txtid)
    val eliminar = view.findViewById<Button>(R.id.btneliminar)
    val editar = view.findViewById<Button>(R.id.btnrditar)
    val cantedit = view.findViewById<EditText>(R.id.editP)

    fun render(producto: ProductoCarrito){
        namepro.text = producto.name
        cantpro.text = producto.cantidadCompra.toString()
        preciopro.text = producto.price
        idproducto.text = producto.id.toString()

        eliminar.setOnClickListener {
            val id = idproducto.text.toString().toInt()
            ProductoProvider.productoList.removeAll { it.id == id }
            adapter.notifyDataSetChanged()
        }
        editar.setOnClickListener{

            val nuevaCantidad = cantedit.text.toString().toInt()
            val id = idproducto.text.toString().toInt()
            ProductoProvider.productoList.find { it.id == id }?.cantidadCompra = nuevaCantidad
            cantpro.text = nuevaCantidad.toString()
            cantedit.text.clear()
            adapter.notifyDataSetChanged()

        }
    }
}