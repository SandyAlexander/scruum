package com.example.maps

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarritoCompras : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.carritocompras)
        initRecycleView()

        val volvercatalogo: Button = findViewById(R.id.iralcarrito)
        volvercatalogo.setOnClickListener {
            val intent = Intent(this, CatalogoActivity::class.java)
            startActivity(intent)
        }

        val pagar: Button = findViewById(R.id.procederalpago)
        pagar.setOnClickListener {
            val intent = Intent(this, FormasPago::class.java)
            startActivity(intent)
        }

        val irubicacion: Button = findViewById(R.id.mapa2)
        irubicacion.setOnClickListener {
            val intent = Intent(this, Ubicacion::class.java)
            startActivity(intent)
        }

    }

    private fun initRecycleView(){
        val recycleView = findViewById<RecyclerView>(R.id.recyclerView)
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = ProductosAdapter(ProductoProvider.productoList)
    }

    fun irubicacion(view: android.view.View){
    }
    fun pagar(view: android.view.View){
    }
}

