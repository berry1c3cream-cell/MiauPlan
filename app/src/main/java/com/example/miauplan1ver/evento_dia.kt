package com.example.miauplan1ver

import android.content.Intent
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

        tvDia.translationX = -100f
        tvDia.alpha = 0f

        tvDia.animate()
            .translationX(0f)
            .alpha(1f)
            .setDuration(500)
            .start()

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

            layoutVacuna.animate()
                .scaleX(1.03f)
                .scaleY(1.03f)
                .setDuration(150)
                .withEndAction {

                    layoutVacuna.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(150)
                        .start()
                }
                .start()
        }

        if (vetActivo) {

            tvVacunaEstado.text = "✅ Visitó veterinario"

            layoutVacuna.animate()
                .scaleX(1.03f)
                .scaleY(1.03f)
                .setDuration(150)
                .withEndAction {

                    layoutVacuna.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(150)
                        .start()
                }
                .start()
        }

        layoutVacuna.animate()
            .alpha(1f)
            .setDuration(500)

        layoutVacuna.setOnTouchListener { v, event ->

            when (event.action) {

                android.view.MotionEvent.ACTION_DOWN -> {
                    v.animate()
                        .alpha(0.85f)
                        .setDuration(80)
                        .start()
                }

                android.view.MotionEvent.ACTION_UP,
                android.view.MotionEvent.ACTION_CANCEL -> {
                    v.animate()
                        .alpha(1f)
                        .setDuration(80)
                        .start()
                }
            }

            false
        }

        layoutVacuna.setOnClickListener {

            it.animate()
                .scaleX(0.92f)
                .scaleY(0.92f)
                .setDuration(80)
                .withEndAction {

                    it.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .duration = 80

                    val intent = Intent(this, vacunas::class.java)

                    intent.putExtra("DIA", dia)

                    startActivity(intent)

                    overridePendingTransition(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                    )
                }
        }

        layoutVet.animate()
            .alpha(1f)
            .setDuration(500)

        layoutVet.setOnTouchListener { v, event ->

            when (event.action) {

                android.view.MotionEvent.ACTION_DOWN -> {
                    v.animate()
                        .alpha(0.85f)
                        .setDuration(80)
                        .start()
                }

                android.view.MotionEvent.ACTION_UP,
                android.view.MotionEvent.ACTION_CANCEL -> {
                    v.animate()
                        .alpha(1f)
                        .setDuration(80)
                        .start()
                }
            }

            false
        }

        layoutVet.setOnClickListener {

            it.animate()
                .scaleX(0.92f)
                .scaleY(0.92f)
                .setDuration(80)
                .withEndAction {

                    it.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .duration = 80

                    val intent = Intent(this, activity_visitaVeterinario::class.java)

                    intent.putExtra("DIA", dia)

                    startActivity(intent)

                    overridePendingTransition(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                    )
                }

        }

        // GUARDAR EVENTO
        btnSave.setOnClickListener {

            val texto = etNotas.text.toString()

            prefs.edit()
                .putString(clave, texto)
                .apply()

            if (texto.isNotEmpty()) {
                tvNotasGuardadas.animate()
                    .alpha(0f)
                    .setDuration(120)
                    .withEndAction {

                        tvNotasGuardadas.text = texto

                        tvNotasGuardadas.animate()
                            .alpha(1f)
                            .setDuration(120)
                            .start()
                    }
                    .start()
            } else {
                tvNotasGuardadas.text = "No hay nada aún"
            }

            Toast.makeText(this, "Evento guardado 😺", Toast.LENGTH_SHORT).show()

            btnSave.animate()
                .rotation(4f)
                .setDuration(50)
                .withEndAction {

                    btnSave.animate()
                        .rotation(-4f)
                        .setDuration(50)
                        .withEndAction {

                            btnSave.animate()
                                .rotation(0f)
                                .setDuration(50)
                                .start()
                        }
                        .start()
                }
                .start()


            finish()
        }

        // CANCELAR
        btnCancel.setOnClickListener {

            btnCancel.animate()
                .alpha(0.6f)
                .setDuration(120)
                .withEndAction {
                    finish()
                }
                .start()

            finish()
        }

        val animatedViews = listOf(
            layoutVacuna,
            layoutVet,
            etNotas,
            tvNotasGuardadas,
            btnSave,
            btnCancel
        )

        etNotas.setOnFocusChangeListener { v, hasFocus ->

            if (hasFocus) {

                v.animate()
                    .scaleX(1.02f)
                    .scaleY(1.02f)
                    .setDuration(120)
                    .start()

            } else {

                v.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(120)
                    .start()
            }
        }

        animatedViews.forEachIndexed { index, view ->

            view.alpha = 0f
            view.translationY = 40f

            view.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay((index * 90).toLong())
                .setDuration(350)
                .start()
        }
    }
}