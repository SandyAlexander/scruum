package com.example.maps

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ClienteLoginActivity : AppCompatActivity() {
    private lateinit var editTextContraseña: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var databaseManager: DatabaseManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cliente_login)

        databaseManager = DatabaseManager(this)

        editTextContraseña = findViewById(R.id.editTextContraseña)
        editTextCorreo = findViewById(R.id.editTextCorreo)


        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)
        val txtRegistrate = findViewById<TextView>(R.id.txtRegistrate)

        btnIniciarSesion.setOnClickListener {
            iniciarSesionCliente()
        }


        txtRegistrate.setOnClickListener {
            abrirVentanaRegistro()
        }
    }
    private fun iniciarSesionCliente() {
        val correo = editTextCorreo.text.toString().toLowerCase() // Convertir a minúsculas
        val contraseña = editTextContraseña.text.toString()

        // Obtener el usuario de la base de datos
        val usuario = databaseManager.obtenerUsuario(correo)

        if (usuario != null && validarContraseña(contraseña, usuario.contrasena)) {
            mostrarMensaje("Inicio de sesión exitoso")

            // Redirigir a la ventana de catálogo
            val intent = Intent(this, CatalogoActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            mostrarMensaje("Correo electrónico o contraseña incorrectos")
        }
    }

    // Método para validar la contraseña utilizando hashing
    private fun validarContraseña(contraseñaIngresada: String, contraseñaAlmacenada: String): Boolean {
        // Aquí debes implementar la lógica para comparar contraseñas de manera segura
        // Puedes utilizar bibliotecas de hashing como Bcrypt o SHA-256
        // Devuelve true si las contraseñas coinciden, false en caso contrario
        // Este es un ejemplo básico, debes adaptarlo a tus necesidades de seguridad
        return contraseñaIngresada == contraseñaAlmacenada
    }


    override fun onDestroy() {
        super.onDestroy()
        databaseManager.cerrarConexion()
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun abrirVentanaRegistro() {
        val intent = Intent(this, RegistroClienteActivity::class.java)
        startActivity(intent)
    }
}

