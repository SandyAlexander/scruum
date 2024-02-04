package com.example.maps

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PrincipalProveedor: AppCompatActivity(){
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.proveedor_principal)
        val btnSendNotification = findViewById<Button>(R.id.btnSendNotification)

        btnSendNotification.setOnClickListener {
            val title = "Confirmación de Entrega"
            val message = "Estimado/a, le confirmamos que su pedido está siendo gestionado para su entrega a domicilio"
            val timestamp = System.currentTimeMillis()

            // Envía la notificación con la hora actual
            Notification.NotificationManager.sendNotification(title, message, timestamp)
        }
            var btnHistorial = findViewById<Button>(R.id.btnHistorial)
            var btnListadoproducto= findViewById<Button>(R.id.btnListadoproducto)
            var btnListadocliente = findViewById<Button>(R.id.btnListadocliente)
            var btnMapa = findViewById<Button>(R.id.btnMapa)

            btnHistorial.setOnClickListener{

            }
            btnListadoproducto.setOnClickListener{
                val intent = Intent(this, ListadoProductosActivity::class.java)
                startActivity(intent)
            }

            val gpedidos= findViewById<Button>(R.id.idgestionpedidos)
            gpedidos.setOnClickListener{
                val intent = Intent(this, AceptarPedidos::class.java)
                startActivity(intent)
            }

        val hpedidos= findViewById<Button>(R.id.btnHistorial)
        hpedidos.setOnClickListener{
            val intent = Intent(this, HistorialPedidos::class.java)
            startActivity(intent)
        }

            btnListadocliente.setOnClickListener{

            }
            btnMapa.setOnClickListener{

            }
        }

}

