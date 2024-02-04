package com.example.maps

import android.annotation.SuppressLint
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

class RegistroProvedor : AppCompatActivity() {

        private lateinit var editTextContraseña: EditText
        private lateinit var editTextVerificarContraseña: EditText

        @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.registro_proveedor)


            editTextContraseña = findViewById(R.id.editTextContraseña)
            editTextVerificarContraseña = findViewById(R.id.editTextVerificarContraseña)

            val btnRegistrarse = findViewById<Button>(R.id.btnRegistarse)
            btnRegistrarse.setOnClickListener {
                if (camposCompletos()) {

                    registrarProveedor()
                } else {

                    mostrarAlerta("Por favor, completa todos los campos.")
                }
            }
        }

        private fun camposCompletos(): Boolean{

            val TextNombre = findViewById<EditText>(R.id.TextNombre)
            val TextApellido = findViewById<EditText>(R.id.TextApellido)
            val TextCedula = findViewById<EditText>(R.id.TextCedula)
            val TextDireccion = findViewById<EditText>(R.id.TextDireccion)
            val TextTelefono = findViewById<EditText>(R.id.TextTelefono)
            val TextCorreo = findViewById<EditText>(R.id.TextCorreo)
            val editTextContraseña = findViewById<EditText>(R.id.editTextContraseña)
            val editTextVerificarContraseña = findViewById<EditText>(R.id.TextVerificarContraseña)


            return (TextNombre.text.isNotEmpty() &&
                    TextApellido.text.isNotEmpty() &&
                    TextCedula.text.isNotEmpty() &&
                    TextDireccion.text.isNotEmpty() &&
                    TextTelefono.text.isNotEmpty() &&
                    TextCorreo.text.isNotEmpty() &&
                   editTextContraseña.text.isNotEmpty() &&
                    editTextVerificarContraseña.text.isNotEmpty())
        }

        private fun registrarProveedor() {

            val contraseña = editTextContraseña.text.toString()
            val verificarContraseña = editTextVerificarContraseña.text.toString()


            if (contraseña != verificarContraseña) {
                mostrarAlerta("Las contraseñas no coinciden. Inténtalo de nuevo.")
                return
            }


            if (!validarContraseña(contraseña)) {
                mostrarAlerta("La contraseña debe tener al menos 8 caracteres y contener números, letras mayúsculas, minúsculas y signos de puntuación.")
                return
            }

            mostrarMensaje("Registro exitoso")
        }

        private fun  validarContraseña(contraseña: String): Boolean {
            val pattern = "(?=,*[0-9])(?=,*[a-z])(?=,*[A-Z])(?=,*[@_,*$#+.-]).{8,}".toRegex()
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

        //Logi
        data class Proveedor(
            val nombre: String,
            val apellido: String,
            val correo: String,
            val contrasena: String
        )

        object proveedorContract {
            object ProveedorEntry{
                const val TABLE_NAME = "proveedors"
                const val COLUMN_NOMBRE = "nombre"
                const val COLUMN_APELLIDO = "apellido"
                const val COLUMN_CORREO = "correo"
                const val COLUMN_CONTRASENA = "contrasena"
            }
        }

        class DatabaseManager(context: Context) {

            private val dbHelper: DatabaseHelper = DatabaseHelper(context)

            fun insertarProveedor(nombre: String, apellido: String, correo: String, contrasena: String): Long {
                val db = dbHelper.writableDatabase
                val values = ContentValues().apply {
                    put(proveedorContract.ProveedorEntry.COLUMN_NOMBRE, nombre)
                    put(proveedorContract.ProveedorEntry.COLUMN_APELLIDO, apellido)
                    put(proveedorContract.ProveedorEntry.COLUMN_CORREO, correo)
                    put(proveedorContract.ProveedorEntry.COLUMN_CONTRASENA, contrasena)
                }
                return db.insert(proveedorContract.ProveedorEntry.TABLE_NAME, null, values)
            }

            fun obtenerProveedor(correo: String): Usuario? {
                val db = dbHelper.readableDatabase
                val projection = arrayOf(
                    proveedorContract.ProveedorEntry.COLUMN_NOMBRE,
                    proveedorContract.ProveedorEntry.COLUMN_APELLIDO,
                    proveedorContract.ProveedorEntry.COLUMN_CORREO,
                    proveedorContract.ProveedorEntry.COLUMN_CONTRASENA
                )

                val selection = "${proveedorContract.ProveedorEntry.COLUMN_CORREO} = ?"
                val selectionArgs = arrayOf(correo)

                val cursor: Cursor = db.query(
                    proveedorContract.ProveedorEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
                )

                return if (cursor.moveToFirst()) {
                    val nombreIndex = cursor.getColumnIndex(proveedorContract.ProveedorEntry.COLUMN_NOMBRE)
                    val apellidoIndex = cursor.getColumnIndex(proveedorContract.ProveedorEntry.COLUMN_APELLIDO)
                    val correoIndex = cursor.getColumnIndex(proveedorContract.ProveedorEntry.COLUMN_CORREO)
                    val contrasenaIndex = cursor.getColumnIndex(proveedorContract.ProveedorEntry.COLUMN_CONTRASENA)

                    // Check if the indices are valid before accessing them
                    if (nombreIndex >= 0 && apellidoIndex >= 0 && correoIndex >= 0 && contrasenaIndex >= 0) {
                        Usuario(
                            cursor.getString(nombreIndex),
                            cursor.getString(apellidoIndex),
                            cursor.getString(correoIndex),
                            cursor.getString(contrasenaIndex)
                        )
                    } else {
                        null
                    }
                } else {
                    null
                }
            }


            fun cerrarConexion() {
                dbHelper.close()
            }
        }

        class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

            companion object {
                const val DATABASE_NAME = "MiBaseDeDatos.db"
                const val DATABASE_VERSION = 1
            }

            override fun onCreate(db: SQLiteDatabase) {
                val SQL_CREATE_ENTRIES =
                    "CREATE TABLE ${proveedorContract.ProveedorEntry.TABLE_NAME} (" +
                            "${proveedorContract.ProveedorEntry.COLUMN_NOMBRE} TEXT," +
                            "${proveedorContract.ProveedorEntry.COLUMN_APELLIDO} TEXT," +
                            "${proveedorContract.ProveedorEntry.COLUMN_CORREO} TEXT PRIMARY KEY," +
                            "${proveedorContract.ProveedorEntry.COLUMN_CONTRASENA} TEXT)"

                db.execSQL(SQL_CREATE_ENTRIES)
            }

            override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
                val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${proveedorContract.ProveedorEntry.TABLE_NAME}"
                db.execSQL(SQL_DELETE_ENTRIES)
                onCreate(db)
            }
        }


}
