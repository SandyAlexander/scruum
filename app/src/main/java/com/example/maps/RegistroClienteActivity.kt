package com.example.maps

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RegistroClienteActivity : AppCompatActivity() {

    private lateinit var editTextContraseña: EditText
    private lateinit var editTextVerificarContraseña: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_cliente)

        //Asegúrate de que estas inicalizando los EditText correctamente
        editTextContraseña = findViewById(R.id.editTextContraseña)
        editTextVerificarContraseña = findViewById(R.id.editTextVerificarContraseña)

        val btnRegistrarse = findViewById<Button>(R.id.btnRegistarse)
        btnRegistrarse.setOnClickListener {
            if (camposCompletos()) {
                //Todos los campos están llenos, procesar registro
                registrarUsuario()
            } else {
                //Mostrar alerta indicando campos obligatorios
                mostrarAlerta("Por favor, completa todos los campos.")
            }
        }
    }

    private fun camposCompletos(): Boolean{

        //Verificación para todos los campos:
        val editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        val editTextApellido = findViewById<EditText>(R.id.editTextApellido)
        val editTextCedula = findViewById<EditText>(R.id.editTextCedula)
        val editTextDireccion = findViewById<EditText>(R.id.editTextDireccion)
        val editTextTelefono = findViewById<EditText>(R.id.editTextTelefono)
        val editTextCorreo = findViewById<EditText>(R.id.editTextCorreo)
        val editTextContraseña = findViewById<EditText>(R.id.editTextContraseña)
        val editTextVerificarContraseña = findViewById<EditText>(R.id.editTextVerificarContraseña)


        return (editTextNombre.text.isNotEmpty() &&
                editTextApellido.text.isNotEmpty() &&
                editTextCedula.text.isNotEmpty() &&
                editTextDireccion.text.isNotEmpty() &&
                editTextTelefono.text.isNotEmpty() &&
                editTextCorreo.text.isNotEmpty() &&
                editTextContraseña.text.isNotEmpty() &&
                editTextVerificarContraseña.text.isNotEmpty())
    }

    private fun registrarUsuario() {
        // Validaciones de contraseñas
        val contraseña = editTextContraseña.text.toString()
        val verificarContraseña = editTextVerificarContraseña.text.toString()

        // Verificar si las contraseñas son iguales
        if (contraseña != verificarContraseña) {
            mostrarAlerta("Las contraseñas no coinciden. Inténtalo de nuevo.")
            return
        }

        // Verificar si la contraseña cumple con los requisitos (10 caracteres, letras, números, etc.)
        if (!validarContraseña(contraseña)) {
            mostrarAlerta("La contraseña debe tener al menos 8 caracteres y contener números, letras mayúsculas, minúsculas y signos de puntuación.")
            return
        }

        // Obtener los valores de los demás campos del formulario
        val nombre = findViewById<EditText>(R.id.editTextNombre).text.toString()
        val apellido = findViewById<EditText>(R.id.editTextApellido).text.toString()
        val cedula = findViewById<EditText>(R.id.editTextCedula).text.toString()
        val direccion = findViewById<EditText>(R.id.editTextDireccion).text.toString()
        val telefono = findViewById<EditText>(R.id.editTextTelefono).text.toString()
        val correo = findViewById<EditText>(R.id.editTextCorreo).text.toString()

        // Insertar el usuario en la base de datos SQLite
        val databaseManager = DatabaseManager(this)
        val nuevoIdUsuario = databaseManager.insertarUsuario(nombre, apellido, correo, contraseña)

        if (nuevoIdUsuario != -1L) {
            // Registro exitoso

            // Verificar si el usuario se encuentra en la base de datos
            val usuarioRegistrado = databaseManager.obtenerUsuario(correo)

            if (usuarioRegistrado != null) {
                mostrarMensaje("Registro exitoso. Usuario: ${usuarioRegistrado.nombre}")
            } else {
                mostrarAlerta("Error al verificar el registro.")
            }
        } else {
            // Error al registrar
            mostrarAlerta("Error al registrar. Inténtalo de nuevo.")
        }

        // Cerrar la conexión a la base de datos
        databaseManager.cerrarConexion()
    }


    private fun  validarContraseña(contraseña: String): Boolean {
        val pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@_,*$#+.-]).{8,}".toRegex()
        return contraseña.matches(pattern)
    }
    private fun mostrarAlerta(mensaje: String){
        //Muestra una alerta utilizando AlertDialog para campos obligatorios no completados
        val builder = AlertDialog.Builder(this)
        builder.setMessage(mensaje)
            .setPositiveButton("Aceptar", null)
            .show()
    }
    private fun mostrarMensaje(mensaje: String) {
        //Muestra un mensaje de éxito
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun togglePasswordVisibility(editText: EditText) {
        val selectionStart = editText.selectionStart
        val selectionEnd = editText.selectionEnd

        if (editText.transformationMethod == null) {
            // Si ya está mostrando la contraseña, ocultarla
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
        } else {
            // Si está ocultando la contraseña, mostrarla
            editText.transformationMethod = null
        }

        // Restaurar la posición del cursor
        editText.setSelection(selectionStart, selectionEnd)
    }

}