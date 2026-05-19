package com.example.miauplan1ver

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val boton = findViewById<FloatingActionButton>(R.id.addCat)
        val btnDelete = findViewById<MaterialButton>(R.id.btnDelete)

        btnDelete.setOnClickListener {

            // BORRAR GATO
            val gatoPrefs = getSharedPreferences("GatosDB", MODE_PRIVATE)
            gatoPrefs.edit().clear().apply()

            // BORRAR EVENTOS
            val eventosPrefs = getSharedPreferences("EventosDB", MODE_PRIVATE)
            eventosPrefs.edit().clear().apply()

            Toast.makeText(this, "Gato eliminado 🐱💨", Toast.LENGTH_SHORT).show()

            cargarDatos()
        }


        boton.setOnClickListener {
            val intent = Intent(this, CrearGatoActivity::class.java)
            startActivity(intent)
        }

        val catCard = findViewById<CardView>(R.id.cardGato)

        catCard.setOnClickListener {

            val prefs = getSharedPreferences("GatosDB", MODE_PRIVATE)
            val nombre = prefs.getString("nombre", "")

            if (!nombre.isNullOrEmpty()) {

                val intent = Intent(this, CalendarioMainActivity::class.java)
                startActivity(intent)

            } else {

                Toast.makeText(this, "Primero agrega un michi 🐱", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onResume() {
        super.onResume()
        cargarDatos()
    }

    fun cargarDatos() {
        val prefs = getSharedPreferences("GatosDB", MODE_PRIVATE)

        val nombre = prefs.getString("nombre", "")
        val edad = prefs.getString("edad", "")
        val raza = prefs.getString("raza", "")
        val peso = prefs.getString("peso", "")
        val genero = prefs.getString("genero", "")
        val catNombre = findViewById<TextView>(R.id.catNombre)
        val catDetalles = findViewById<TextView>(R.id.catDetalles)
        val boton = findViewById<FloatingActionButton>(R.id.addCat)
        val btnDelete = findViewById<MaterialButton>(R.id.btnDelete)

        val generoTexto = when (genero) {
            "M" -> "Masculino"
            "F" -> "Femenino"
            else -> "Desconocido"
        }

        if (!nombre.isNullOrEmpty()) {

            catNombre.text = nombre

            catDetalles.text = """
                $generoTexto • $edad meses
                $raza • $peso kg
            """.trimIndent()

            boton.hide()
            btnDelete.visibility = View.VISIBLE

        } else {

            catNombre.text = "Aún no tienes gatos"

            catDetalles.text = "Agrega tu primer michi 🐱"

            boton.show()
            btnDelete.visibility = View.GONE
        }
    }
}