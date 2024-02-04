package com.example.maps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AgregarProducto : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agregar_producto)

        val btnSubirImagen: Button = findViewById(R.id.btnSubirImagen)
        btnSubirImagen.setOnClickListener {
            abrirGaleria()
        }

        val btnAgregarProducto: Button = findViewById(R.id.btnAgregarProducto)
        btnAgregarProducto.setOnClickListener {
            val nombreProducto = findViewById<EditText>(R.id.editTextNombreProducto).text.toString()
            val descripcion = findViewById<EditText>(R.id.editTextDescripcion).text.toString()
            val precio = findViewById<EditText>(R.id.editTextPrecio).text.toString()
            val cantidad = findViewById<EditText>(R.id.editTextCantidad).text.toString()

            val resultadoIntent = Intent()
            resultadoIntent.putExtra("nombreProducto", nombreProducto)
            resultadoIntent.putExtra("descripcion", descripcion)
            resultadoIntent.putExtra("precio", precio)
            resultadoIntent.putExtra("cantidad", cantidad)

            setResult(Activity.RESULT_OK, resultadoIntent)
            finish()
        }
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
}
