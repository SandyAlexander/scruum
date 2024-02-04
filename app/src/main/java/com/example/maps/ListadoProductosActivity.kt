package com.example.maps

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity



class ListadoProductosActivity : AppCompatActivity(), DatabaseManager.DatabaseListener {

    private lateinit var adaptadorProductos: ArrayAdapter<Producto>
    private val listaProductos = mutableListOf<Producto>()
    private lateinit var databaseManager: DatabaseManager

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_productos)

        // Inicializar DatabaseManager
        databaseManager = DatabaseManager(this)

        // Establecer el listener para escuchar eventos de cambio en la base de datos
        databaseManager.setDatabaseListener(this)


        val listViewProductos: ListView = findViewById(R.id.listViewProductos)

        // Obtener la lista de productos desde la base de datos

        adaptadorProductos = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaProductos
        )

        listViewProductos.adapter = adaptadorProductos

        cargarListaProductos()

        val btnAgregarNuevoProducto: Button = findViewById(R.id.btnAgregarNuevoProducto)
        btnAgregarNuevoProducto.setOnClickListener {
            // Abre la actividad AgregarProducto
            val intent = Intent(this, AgregarProducto::class.java)
            startActivityForResult(intent, CODIGO_RESULTADO_AGREGAR_PRODUCTO)
        }

        // Agregar el listener para mostrar el diálogo de editar/eliminar al hacer clic en un elemento
        listViewProductos.setOnItemClickListener { _, _, position, _ ->
            showEditDeleteDialog(position)
        }
    }

    private fun cargarListaProductos() {
        try {
            // Verificar si la lista de productos ya está cargada
            if (listaProductos.isEmpty()) {
                // Obtener la lista de productos desde la base de datos
                val productosDB = databaseManager.obtenerProductos()

                // Limpiar y agregar los productos a la lista
                listaProductos.clear()
                listaProductos.addAll(productosDB)
            }

            // Notificar al adaptador sobre el cambio
            adaptadorProductos.notifyDataSetChanged()
        } catch (e: Exception) {
            // Manejar la excepción según tus necesidades
            e.printStackTrace()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        // Cierra la conexión con la base de datos al destruir la actividad
        databaseManager.cerrarConexion()
    }

    override fun onProductoInsertado() {
        // Se llama cuando un producto se inserta en la base de datos
        // Actualiza la lista de productos y notifica al adaptador
        cargarListaProductos()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODIGO_RESULTADO_AGREGAR_PRODUCTO && resultCode == RESULT_OK) {
            // Obtén los datos del producto desde el Intent
            val nombreProducto = data?.getStringExtra("nombreProducto") ?: ""
            val descripcion = data?.getStringExtra("descripcion") ?: ""
            val precio = data?.getStringExtra("precio") ?: ""
            val cantidad = data?.getStringExtra("cantidad") ?: ""

            // Inserta el nuevo producto en la base de datos
            databaseManager.insertarProducto(nombreProducto, descripcion, precio, 0, cantidad.toInt())
        }
    }

    companion object {
        const val CODIGO_RESULTADO_AGREGAR_PRODUCTO = 1
    }

    private fun showEditDeleteDialog(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar o Eliminar")
            .setItems(arrayOf("Editar", "Eliminar")) { _, which ->
                when (which) {
                    0 -> showEditDialog(position)
                    1 -> showDeleteDialog(position)
                }
            }
            .show()
    }

    private fun showEditDialog(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Producto")

        val producto = listaProductos[position]

        // Personalizar el formulario de edición aquí
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val etNombre = EditText(this)
        etNombre.hint = "Nombre"
        etNombre.setText(producto.name)
        layout.addView(etNombre)

        val etDescripcion = EditText(this)
        etDescripcion.hint = "Descripción"
        etDescripcion.setText(producto.description)
        layout.addView(etDescripcion)

        val etPrecio = EditText(this)
        etPrecio.hint = "Precio"
        etPrecio.setText(producto.price)
        layout.addView(etPrecio)

        val etCantidad = EditText(this)
        etCantidad.hint = "Cantidad"
        etCantidad.setText(producto.availableQuantity.toString())
        layout.addView(etCantidad)

        builder.setView(layout)

        builder.setPositiveButton("Guardar") { _, _ ->
            try {
                // Obtener los valores editados
                val editedNombre = etNombre.text.toString()
                val editedDescripcion = etDescripcion.text.toString()
                val editedPrecio = etPrecio.text.toString()
                val editedCantidad = etCantidad.text.toString().toInt()

                // Actualizar los valores en el objeto producto
                producto.name = editedNombre
                producto.description = editedDescripcion
                producto.price = editedPrecio
                producto.availableQuantity = editedCantidad

                // Actualizar la base de datos
                databaseManager.actualizarProducto(producto)

                // Notificar al adaptador sobre el cambio
                adaptadorProductos.notifyDataSetChanged()
            } catch (e: NumberFormatException) {
                // Manejar la excepción si el usuario ingresó un valor no válido
                e.printStackTrace()
            }
        }

        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun showDeleteDialog(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar Producto")
            .setMessage("¿Estás seguro de que deseas eliminar este producto?")
            .setPositiveButton("Sí") { _, _ ->
                // Implementar la lógica para eliminar el producto
                val producto = listaProductos[position]
                databaseManager.eliminarProducto(producto.id)
                listaProductos.removeAt(position)
                adaptadorProductos.notifyDataSetChanged()
            }
            .setNegativeButton("No", null)
            .show()
    }
}
