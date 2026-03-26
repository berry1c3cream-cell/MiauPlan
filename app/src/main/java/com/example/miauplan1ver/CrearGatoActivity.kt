package com.example.miauplan1ver

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class CrearGatoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.add_cat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSave = findViewById<com.google.android.material.button.MaterialButton>(R.id.btnSave)
        val btnCancel = findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCancel)
        val etNombre = findViewById<TextInputEditText>(R.id.etNombre)

        // SAVE → nueva pantalla
        btnSave.setOnClickListener {
            val nombre = etNombre.text.toString()

            val intent = Intent(this, CalendarioMainActivity::class.java)
            intent.putExtra("NOMBRE_GATO", nombre)

            if (nombre.isNotEmpty()) {
                val intent = Intent(this, CalendarioMainActivity::class.java)
                intent.putExtra("NOMBRE_GATO", nombre)
                startActivity(intent)
            } else {
                etNombre.error = "Ponle nombre al michi!!"
            }
        }

        // CANCEL → regresar a la pantalla anterior
        btnCancel.setOnClickListener {
            finish()
        }
    }
}