package com.example.miauplan1ver

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class vacunas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vacunas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val checkRabia = findViewById<CheckBox>(R.id.checkRabia)
        val checkTriple = findViewById<CheckBox>(R.id.checkTripleFelina)
        val checkLeucemia = findViewById<CheckBox>(R.id.checkLeucemia)
        val checkBordetella = findViewById<CheckBox>(R.id.checkBordetella)
        val dia = intent.getIntExtra("DIA", 1)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnCancel = findViewById<Button>(R.id.btnCancelar)

        val claveRabia = "rabia_$dia"
        val claveTriple = "triple_$dia"
        val claveLeucemia = "leucemia_$dia"
        val claveBordetella = "bordetella_$dia"

        // CARGAR DATOS GUARDADOS
        val prefs = getSharedPreferences("VacunasDB", MODE_PRIVATE)

        checkRabia.isChecked =
            prefs.getBoolean(claveRabia, false)

        checkTriple.isChecked =
            prefs.getBoolean(claveTriple, false)

        checkLeucemia.isChecked =
            prefs.getBoolean(claveLeucemia, false)

        checkBordetella.isChecked =
            prefs.getBoolean(claveBordetella, false)

        // GUARDAR DATOS
        btnGuardar.setOnClickListener {

            prefs.edit()
                .putBoolean(claveRabia, checkRabia.isChecked)
                .putBoolean(claveTriple, checkTriple.isChecked)
                .putBoolean(claveLeucemia, checkLeucemia.isChecked)
                .putBoolean(claveBordetella, checkBordetella.isChecked)
                .apply()

            Toast.makeText(this,
                "Vacunas guardadas 🐱💉",
                Toast.LENGTH_SHORT).show()

            finish()

        }

        // CANCELAR
        btnCancel.setOnClickListener {
            finish()
        }
    }
}