package com.example.maps

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProvedorLogin: AppCompatActivity() {
    private lateinit var TextContraseña: EditText
    private lateinit var TextCorreo: EditText
    private lateinit var databaseManager: DatabaseManager

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.provedor_login)

        databaseManager = DatabaseManager(this)

        TextContraseña = findViewById(R.id.TextContraseña)
        TextCorreo = findViewById(R.id.TextCorreo)

        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)
        val idRegistrate = findViewById<TextView>(R.id.idRegistrate)
        btnIniciarSesion.setOnClickListener {
            iniciarSesionProveedor()
        }
        idRegistrate.setOnClickListener {
            abrirVentana()
        }
    }
    private fun iniciarSesionProveedor() {
        val correo = TextCorreo.text.toString()
        val contraseña = TextContraseña.text.toString()

        if (correo == "Cristhian" && contraseña == "Ecuador123@") {
            mostrarMensaje("Inicio de sesión exitoso")
            val intent = Intent(/* packageContext = */ this, /* cls = */ PrincipalProveedor::class.java)
            startActivity(intent)
            finish()
        } else {
            mostrarMensaje("Nombre de usuario o contraseña incorrectos")
        }
    }



    /*private fun iniciarSesionProveedor() {
        val correo = editTextCorreo.text.toString()
        val contraseña = editTextContraseña.text.toString()
        val usuario = databaseManager.obtenerUsuario(correo)

        if (usuario != null) {
            if (usuario.contrasena == contraseña) {
                mostrarMensaje("Inicio de sesión exitoso")
            } else {
                mostrarMensaje("Contraseña incorrecta")
            }
        } else {
            mostrarMensaje("El correo no está registrado")
        }
    }*/

    override fun onDestroy() {
        super.onDestroy()
        databaseManager.cerrarConexion()
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun abrirVentana() {
        val intent = Intent(this, RegistroProvedor::class.java)
        startActivity(intent)
    }
}