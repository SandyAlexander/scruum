package com.example.maps

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu

class PerfilClienteActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)

        val imageViewMenu = findViewById<View>(R.id.imageViewMenu)

        imageViewMenu.setOnClickListener { showPopupMenu(imageViewMenu) }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_perfil_cliente, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_ver_perfil -> {
                    // Lógica para ver el perfil
                    // Puedes abrir una nueva actividad o fragmento para mostrar la información del perfil
                    true
                }
                R.id.action_editar_perfil -> {
                    // Lógica para editar el perfil
                    // Puedes abrir una nueva actividad o fragmento para permitir la edición del perfil
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }
}