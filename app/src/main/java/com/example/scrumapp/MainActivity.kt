package com.example.scrumapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnProveedor = findViewById<Button>(R.id.button)
        btnProveedor.setOnClickListener {
            mostrarDialogo()
        }
    }

    private fun mostrarDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ingrese la clave de acceso")

        // Agrega un campo de texto al cuadro de diálogo
        val input = EditText(this)
        builder.setView(input)

        // Configura los botones "Aceptar" y "Cancelar"
        builder.setPositiveButton("Continuar") { _, _ ->
            val claveIngresada = input.text.toString()

            // Verifica si la clave ingresada es correcta
            if (claveIngresada == "22006023") {
                // Muestra un mensaje si la clave es correcta
                mostrarMensaje("Clave correcta")
            } else {
                // Muestra un mensaje si la clave es incorrecta
                mostrarMensaje("Clave incorrecta")
            }
        }
        builder.setNegativeButton("Cancelar") { _, _ ->
            // Acción a realizar al hacer clic en "Cancelar"
        }

        // Muestra el cuadro de diálogo
        builder.show()
    }

    private fun mostrarMensaje(mensaje: String) {
        // Muestra un Toast con el mensaje
        android.widget.Toast.makeText(this, mensaje, android.widget.Toast.LENGTH_SHORT).show()
    }
}
