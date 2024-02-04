package com.example.maps

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AceptarPedidos : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aceptar_pedidos)
        initRecycleView()



        // Ejemplo de manejo del clic en el botón "Aceptar"
        //Para enviar la notificación cuaNDO SE ACEPTA EL PEDIDO
        val btnSendNotification = findViewById<Button>(R.id.botonAceptar)
        btnSendNotification.setOnClickListener {
            val title = "Confirmación de Entrega"
            val message = "Estimado/a, le confirmamos que su pedido está siendo gestionado para su entrega a domicilio"
            val timestamp = System.currentTimeMillis()

            // Envía la notificación con la hora actual
            Notification.NotificationManager.sendNotification(title, message, timestamp)
        }
        //HASTA AQUI NOTIFI..


        val SendNotification = findViewById<Button>(R.id.botonRechazar)
        SendNotification.setOnClickListener {
            val title = "Productos agotados"
            val message = "Estimado/a, Pedimos Disculpas nuestros productos se encuentran agotados"
            val timestamp = System.currentTimeMillis()

            // Envía la notificación con la hora actual
            Notification.NotificationManager.sendNotification(title, message, timestamp)
        }
    }
    private fun initRecycleView(){
        val recycleView = findViewById<RecyclerView>(R.id.recyclerViewAceptarPedidos)
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = ProductosAdapter(ProductoProvider.productoList)
    }
}
