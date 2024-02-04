package com.example.maps

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class  Paginaprincipal : AppCompatActivity() {

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.paginaprincipal)

                val btnProveedor = findViewById<Button>(R.id.button)
                btnProveedor.setOnClickListener {
                    mostrarDialogo()
                }
                //En el MainActivity, clic en el botono Cliente
                val btnCliente = findViewById<Button>(R.id.buttonm)
                btnCliente.setOnClickListener {
                    val intent = Intent(this,ClienteLoginActivity::class.java)
                    startActivity(intent)
                }
            }

    private fun mostrarDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ingrese la clave de acceso")

        //Agrega un campo de texto al caudro de dialogo
        val input = EditText(this)
        builder.setView(input)

        //configuracion de los botones "Aceptar" y "Cancelar"
        builder.setPositiveButton("Continuar") { _, _ ->
            val claveIngresada = input.text.toString()

            //Verificar si la clave ingresada es correcta
            if (claveIngresada == "22006023"){
                //Muestra un mensaje si la clave es correcta
                mostrarMensaje("Clave correcta")
                val intent = Intent (this,ProvedorLogin:: class.java )
                startActivity(intent)
            } else {
                //Muestra un mensaje si la clave es incorrecta
                mostrarMensaje("Calve incorrecta")
            }
        }

        //Muestra el cuadro de dialogo
        builder.show()
    }

    private fun mostrarMensaje(mensaje: String) {
        //Muestra un Toast con el mensaje
        android.widget.Toast.makeText(this, mensaje, android.widget.Toast.LENGTH_SHORT).show()
    }

}