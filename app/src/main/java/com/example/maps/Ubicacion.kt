package com.example.maps

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Ubicacion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ubicaciouno)

        val volver: Button = findViewById(R.id.valver)
        volver.setOnClickListener {
            val intent = Intent(this, CarritoCompras::class.java)
            startActivity(intent)
        }
        val irmaps: Button = findViewById(R.id.pais)
        irmaps.setOnClickListener {
            val intent = Intent(this, Orellana::class.java)
            startActivity(intent)
        }


        // Obtener los componentes
        val editText = findViewById<EditText>(R.id.editTextText)
        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)

        // Agregar un oyente al botón
        button.setOnClickListener {
            // Obtener el texto del EditText
            val text = editText.text.toString()

            // Actualizar el TextView
            textView.text = text
        }
    }
    fun volver(view: android.view.View){
    }
    fun irmaps(view: android.view.View){
    }
}
