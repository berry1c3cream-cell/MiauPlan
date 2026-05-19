package com.example.miauplan1ver

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EventoDiaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.evento_dia)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dia = intent.getIntExtra("DIA", 1)

        val tvDia = findViewById<TextView>(R.id.tvDia)
        val etNotas = findViewById<EditText>(R.id.etNotas)

        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnCancel = findViewById<Button>(R.id.btnCancel)
        val tvNotasGuardadas = findViewById<TextView>(R.id.tvNotasGuardadas)
        val layoutVacuna = findViewById<LinearLayout>(R.id.layoutVacuna)
        val layoutVet = findViewById<LinearLayout>(R.id.layoutVet)

        val tvVacunaEstado = findViewById<TextView>(R.id.tvVacunaEstado)
        val tvVetEstado = findViewById<TextView>(R.id.tvVetEstado)

        tvDia.text = "Día $dia"

        val clave = "evento_$dia"
        val claveVacuna = "vacuna_$dia"
        val claveVet = "vet_$dia"

        val prefs = getSharedPreferences("EventosDB", MODE_PRIVATE)

        // CARGAR EVENTO GUARDADO
        val eventoGuardado = prefs.getString(clave, "")

        if (!eventoGuardado.isNullOrEmpty()) {
            tvNotasGuardadas.text = eventoGuardado
        }

        etNotas.setText(eventoGuardado)

        val vacunaActiva = prefs.getBoolean(claveVacuna, false)
        val vetActivo = prefs.getBoolean(claveVet, false)

        if (vacunaActiva) {
            tvVacunaEstado.text = "✅ Vacuna aplicada"
        }

        if (vetActivo) {
            tvVetEstado.text = "✅ Visitó veterinario"
        }

        layoutVacuna.setOnClickListener {

            val nuevoEstado = !prefs.getBoolean(claveVacuna, false)

            prefs.edit()
                .putBoolean(claveVacuna, nuevoEstado)
                .apply()

            if (nuevoEstado) {
                tvVacunaEstado.text = "✅ Vacuna aplicada"
            } else {
                tvVacunaEstado.text = "¿Tu gato ha recibido una vacuna hoy?"
            }
        }

        layoutVet.setOnClickListener {

            val nuevoEstado = !prefs.getBoolean(claveVet, false)

            prefs.edit()
                .putBoolean(claveVet, nuevoEstado)
                .apply()

            if (nuevoEstado) {
                tvVetEstado.text = "✅ Visitó veterinario"
            } else {
                tvVetEstado.text = "¿Has llevado a tu gato a un chequeo hoy?"
            }
        }

        // GUARDAR EVENTO
        btnSave.setOnClickListener {

            val texto = etNotas.text.toString()

            prefs.edit()
                .putString(clave, texto)
                .apply()

            if (texto.isNotEmpty()) {
                tvNotasGuardadas.text = texto
            } else {
                tvNotasGuardadas.text = "No hay nada aún"
            }

            Toast.makeText(this, "Evento guardado 😺", Toast.LENGTH_SHORT).show()


            finish()
        }

        // CANCELAR
        btnCancel.setOnClickListener {
            finish()
        }
    }
}