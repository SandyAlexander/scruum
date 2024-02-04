// MainActivity.kt
package com.example.maps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class CatalogoActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private val VISUALIZAR_NOTIFICACIONES_REQUEST = 1
    private lateinit var notificacionCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)

        dbHelper = DatabaseHelper(this)

        // Obtén los productos directamente desde la función getProductList()
        val products = getProductList()

        // Almacena los productos en la base de datos
        for (product in products) {
            dbHelper.writableDatabase.insert(DatabaseHelper.TABLE_NAME, null, product.toContentValues())
        }

        // Muestra los productos en el GridView
        val adapter = AdaptadorProducto(this, products)
        val productGridView: GridView = findViewById(R.id.productGridView)
        productGridView.adapter = adapter

        notificacionCount = findViewById(R.id.notificacionCount)

        // Actualizar automáticamente el contador al iniciar la actividad
        actualizarContadorNotificaciones()

        val leernotificaciones: ImageView = findViewById(R.id.icnotificacion)
        leernotificaciones.setOnClickListener {
            val intent = Intent(this, VisualizarNotificaciones::class.java)
            startActivityForResult(intent, VISUALIZAR_NOTIFICACIONES_REQUEST)
        }
    }

    override fun onResume() {
        super.onResume()

        // Puedes llamar a la actualización cada vez que la actividad se reanuda (opcional)
        // Esto asegura que el contador se actualice si vuelves a la actividad desde otra pantalla
        actualizarContadorNotificaciones()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == VISUALIZAR_NOTIFICACIONES_REQUEST && resultCode == Activity.RESULT_OK) {
            // Marcar notificaciones como vistas o eliminarlas según sea necesario
            Notification.NotificationManager.marcarNotificacionesComoVistas()

            // Reiniciar el contador a 0
            reiniciarContador()
        }
    }

    private fun reiniciarContador() {
        notificacionCount.text = "0"
    }

    private fun actualizarContadorNotificaciones() {
        // Filtrar las notificaciones que no están marcadas como vistas
        val notificacionesNoVistas = Notification.NotificationManager.getNotifications()
            .filter { !it.vista }
            .toList()

        notificacionCount.text = notificacionesNoVistas.size.toString()
    }

    private fun getProductList(): List<Producto> {
        var productIdCounter = 0
        return listOf(
            Producto(productIdCounter++, "Botella 625ML Aqua Premium", "$1.00", "Balance único de minerales", R.drawable.botella, 10),
            Producto(productIdCounter++, "Botellón 20l Aqua Premium (Liquído)", "$3.00", "Agua purificada PH neutro", R.drawable.botellon, 5),
            Producto(productIdCounter++, "Agua Sin Gas Cielo Botella 2.5 L.", "$2.90", "Disfruta de la pureza y frescura del agua con nuestra botella de agua sin gas Cielo", R.drawable.botella_litro, 8),
            Producto(productIdCounter++, "Funda aguazul 100ml", "0.50 ctvs", " Mantén tu hidratación al alcance con nuestra funda de agua premium", R.drawable.funda, 20),
            Producto(productIdCounter++, "Galón de GAR Water 4.5L", "0.75 ctvs", "Eleva tu experiencia de hidratación con nuestro galón de agua de alta calidad", R.drawable.galon, 15),
        )
    }


}

