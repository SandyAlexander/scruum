package com.example.maps

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData

class DetallesProductos : AppCompatActivity() {

    private val quantityLiveData = MutableLiveData<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalle_producto)

        var id: Int;
        var name: String;
        var price: String;
        var imageResId: Int;
        var quantity = 1

        val product = intent.getSerializableExtra("product") as? Producto

        if (product != null) {
            val productImageView: ImageView = findViewById(R.id.productImageView)
            val productNameTextView: TextView = findViewById(R.id.productNameTextView)
            val productPriceTextView: TextView = findViewById(R.id.productPriceTextView)
            val productDescriptionTextView: TextView = findViewById(R.id.productDescriptionTextView)

            productImageView.setImageResource(product.imageResId)
            productNameTextView.text = product.name
            productPriceTextView.text = product.price
            productDescriptionTextView.text = product.description

            val decrementButton: Button = findViewById(R.id.decrementButton)
            val incrementButton: Button = findViewById(R.id.incrementButton)
            val quantityEditText: EditText = findViewById(R.id.quantityEditText)

            incrementButton.setOnClickListener {
                quantity++
                quantityEditText.setText(quantity.toString())
                quantityLiveData.value = quantity
            }
            decrementButton.setOnClickListener {
                if (quantity > 1) {
                    quantity--
                    quantityEditText.setText(quantity.toString())
                    quantityLiveData.value = quantity
                }
            }
        }
        val continueShoppingButton: Button = findViewById(R.id.continueShoppingButton)
        continueShoppingButton.setOnClickListener {
            val intent = Intent(this, CatalogoActivity::class.java)
            startActivity(intent)
        }
        val agregarcarrito: Button = findViewById(R.id.agregar)
        agregarcarrito.setOnClickListener {
            val existingProduct = ProductoProvider.productoList.find { it.name == product?.name ?: "SN" }
            if (existingProduct != null) {
                existingProduct.cantidadCompra += quantityLiveData.value ?: 1
            } else {
                if (product != null) {
                    ProductoProvider.productoList.add(
                        ProductoCarrito(
                            id = ProductoProvider.productoList.size + 1,
                            name = product.name,
                            price = product.price,
                            cantidadCompra = quantityLiveData.value ?: 1
                        )
                    )
                }
            }
            val intent = Intent(this, CarritoCompras::class.java)
            startActivity(intent)
        }
    }
    fun continueShopping(view: android.view.View) {
    }
    fun agregarcarrito(view: android.view.View) {
    }
}
