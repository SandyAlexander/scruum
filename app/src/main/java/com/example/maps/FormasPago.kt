package com.example.maps

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FormasPago : AppCompatActivity() {

    private lateinit var bankDepositDetailsTextView: TextView
    private lateinit var cashOnDeliveryDetailsTextView: TextView
    private lateinit var btnCashOnDelivery: Button
    private lateinit var btnPurchase: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.formas_pago)

        // Inicializar las referencias a TextView y Button
        bankDepositDetailsTextView = findViewById(R.id.txtBankDepositDetails)
        cashOnDeliveryDetailsTextView = findViewById(R.id.txtCashOnDeliveryDetails)
        btnCashOnDelivery = findViewById(R.id.btnCashOnDelivery)
        btnPurchase = findViewById(R.id.btnPurchase)
    }

    fun onPaymentMethodSelected(view: View) {
        when (view.id) {
            R.id.btnBankDeposit -> {
                val bankDetails = "Banco 1: xxx-xxx-xxxx\nBanco 2: xxx-xxx-xxxx"
                val additionalMessage = "Por favor, envía la captura del comprobante al WhatsApp de la empresa."
                bankDepositDetailsTextView.text = "$bankDetails\n$additionalMessage"
                bankDepositDetailsTextView.visibility = View.VISIBLE
                cashOnDeliveryDetailsTextView.visibility = View.GONE
                btnCashOnDelivery.visibility = View.VISIBLE
                btnPurchase.visibility = View.GONE
                showToast("Pago mediante depósito bancario seleccionado")
            }
            R.id.btnCashOnDelivery -> {
                cashOnDeliveryDetailsTextView.text = "Pago contra entrega seleccionado"
                cashOnDeliveryDetailsTextView.visibility = View.VISIBLE
                bankDepositDetailsTextView.visibility = View.GONE
                btnCashOnDelivery.visibility = View.VISIBLE
                btnPurchase.visibility = View.VISIBLE
                showToast("Pago contra entrega seleccionado. Se notificará a la empresa.")
            }
        }
        adjustLayout(view.id)
    }

    private fun adjustLayout(selectedButtonId: Int) {
        val paramsCashOnDelivery = btnCashOnDelivery.layoutParams as androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
        val paramsPurchase = btnPurchase.layoutParams as androidx.constraintlayout.widget.ConstraintLayout.LayoutParams

        if (selectedButtonId == R.id.btnBankDeposit) {
            // Ajustar la posición de btnCashOnDelivery y btnPurchase debajo de txtBankDepositDetails
            paramsCashOnDelivery.topToBottom = R.id.txtBankDepositDetails
            paramsPurchase.topToBottom = R.id.txtBankDepositDetails
        } else if (selectedButtonId == R.id.btnCashOnDelivery) {
            // Ajustar la posición de btnCashOnDelivery y btnPurchase debajo de txtCashOnDeliveryDetails
            paramsCashOnDelivery.topToBottom = R.id.txtCashOnDeliveryDetails
            paramsPurchase.topToBottom = R.id.txtCashOnDeliveryDetails
        }

        btnCashOnDelivery.layoutParams = paramsCashOnDelivery
        btnPurchase.layoutParams = paramsPurchase

        btnCashOnDelivery.requestLayout() // Forzar la actualización del diseño
        btnPurchase.requestLayout() // Forzar la actualización del diseño
    }

    fun onPurchaseClicked(view: View) {
        showToast("Compra realizada. ¡Gracias por tu compra!")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
