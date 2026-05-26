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

class activity_visitaVeterinario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.visita_veterinario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val checkCheckup = findViewById<CheckBox>(R.id.checkCheckup)
        val checkDesparasitacion = findViewById<CheckBox>(R.id.checkDesparasitacion)
        val checkBano = findViewById<CheckBox>(R.id.checkBano)
        val checkUnas = findViewById<CheckBox>(R.id.checkUnas)
        val checkRevision = findViewById<CheckBox>(R.id.checkRevision)
        val checkVacunas = findViewById<CheckBox>(R.id.checkVacunas)
        val dia = intent.getIntExtra("DIA", 1)

        val claveCheckup = "checkup_$dia"
        val claveBano = "bano_$dia"
        val claveDesparacitacion = "desparacitacion_$dia"
        val claveUnas = "unas_$dia"
        val claveRevision = "revision_$dia"
        val claveVacunas = "vacuna_$dia"

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnCancel = findViewById<Button>(R.id.btnCancelar)

        // CARGAR DATOS GUARDADOS
        val prefs = getSharedPreferences("VacunasDB", MODE_PRIVATE)

        checkCheckup.isChecked =
            prefs.getBoolean(claveCheckup, false)

        checkDesparasitacion.isChecked =
            prefs.getBoolean(claveDesparacitacion, false)

        checkBano.isChecked =
            prefs.getBoolean(claveBano, false)

        checkUnas.isChecked =
            prefs.getBoolean(claveUnas, false)

        checkRevision.isChecked =
            prefs.getBoolean(claveRevision, false)

        checkVacunas.isChecked =
            prefs.getBoolean(claveVacunas, false)

        btnGuardar.setOnClickListener {

            prefs.edit()
                .putBoolean(claveCheckup, checkCheckup.isChecked)
                .putBoolean(claveDesparacitacion, checkDesparasitacion.isChecked)
                .putBoolean(claveBano, checkBano.isChecked)
                .putBoolean(claveUnas, checkUnas.isChecked)
                .putBoolean(claveRevision, checkRevision.isChecked)
                .putBoolean(claveVacunas, checkVacunas.isChecked)
                .apply()

            Toast.makeText(this,
                "Motivo de visita guardado 🐱",
                Toast.LENGTH_SHORT).show()

            finish()
        }

        // CANCELAR
        btnCancel.setOnClickListener {
            finish()
        }
    }
}