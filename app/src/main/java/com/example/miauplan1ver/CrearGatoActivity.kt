package com.example.miauplan1ver

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
        val etEdad = findViewById<TextInputEditText>(R.id.etEdad)
        val etRaza = findViewById<TextInputEditText>(R.id.etRaza)
        val etPeso = findViewById<TextInputEditText>(R.id.etPeso)
        val toggleGenero = findViewById<com.google.android.material.button.MaterialButtonToggleGroup>(R.id.toggleGenero)
        val prefs = getSharedPreferences("GatosDB", MODE_PRIVATE)
        val generoGuardado = prefs.getString("genero", "")

        when (generoGuardado) {
            "M" -> toggleGenero.check(R.id.btnMasculino)
            "F" -> toggleGenero.check(R.id.btnFemenino)
        }

        etNombre.setText(prefs.getString("nombre", ""))
        etEdad.setText(prefs.getString("edad", ""))
        etRaza.setText(prefs.getString("raza", ""))
        etPeso.setText(prefs.getString("peso", ""))


        // SAVE → nueva pantalla
        btnSave.setOnClickListener {

            val nombre = etNombre.text.toString()

            if (nombre.isNotEmpty()) {

                val prefs = getSharedPreferences("GatosDB", MODE_PRIVATE)
                val editor = prefs.edit()

                editor.putString("nombre", etNombre.text.toString())
                editor.putString("edad", etEdad.text.toString())
                editor.putString("raza", etRaza.text.toString())
                editor.putString("peso", etPeso.text.toString())
                val genero = when (toggleGenero.checkedButtonId) {
                    R.id.btnMasculino -> "M"
                    R.id.btnFemenino -> "F"
                    else -> ""
                }

                editor.putString("genero", genero)

                editor.apply()

                Toast.makeText(this, "Gato guardado 🐱", Toast.LENGTH_SHORT).show()

                finish()

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