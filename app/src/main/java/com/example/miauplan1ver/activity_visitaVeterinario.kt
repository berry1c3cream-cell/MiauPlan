package com.example.miauplan1ver

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
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

        val cards = listOf(
            findViewById<View>(R.id.cardCheckup),
            findViewById<View>(R.id.cardDesparasitacion),
            findViewById<View>(R.id.cardBano),
            findViewById<View>(R.id.cardUnas),
            findViewById<View>(R.id.cardRevision),
            findViewById<View>(R.id.cardVacunas)
        )

        cards.forEachIndexed { index, card ->

            card.alpha = 0f
            card.translationY = 50f

            card.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay((index * 90).toLong())
                .setDuration(350)
                .start()
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

        val titulo = findViewById<TextView>(R.id.tvTituloVet)

        titulo.alpha = 0f
        titulo.translationX = -80f

        titulo.animate()
            .alpha(1f)
            .translationX(0f)
            .setDuration(500)
            .start()

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnCancel = findViewById<Button>(R.id.btnCancelar)

        // CARGAR DATOS GUARDADOS
        val prefs = getSharedPreferences("VetDB", MODE_PRIVATE)

        if (checkCheckup.isChecked) {

            findViewById<View>(R.id.cardCheckup)
                .animate()
                .scaleX(1.02f)
                .scaleY(1.02f)
                .setDuration(150)
                .withEndAction {

                    findViewById<View>(R.id.cardCheckup)
                        .animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(150)
                        .start()
                }
                .start()
        }

        fun animateCheckbox(checkBox: CheckBox) {

            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->

                buttonView.animate()
                    .scaleX(1.25f)
                    .scaleY(1.25f)
                    .setDuration(120)
                    .withEndAction {

                        buttonView.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(120)
                            .start()
                    }
                    .start()
            }
        }

        animateCheckbox(checkCheckup)
        animateCheckbox(checkDesparasitacion)
        animateCheckbox(checkBano)
        animateCheckbox(checkUnas)
        animateCheckbox(checkRevision)
        animateCheckbox(checkVacunas)

        fun animateCard(card: View) {

            card.setOnTouchListener { v, event ->

                when (event.action) {

                    android.view.MotionEvent.ACTION_DOWN -> {

                        v.animate()
                            .scaleX(0.97f)
                            .scaleY(0.97f)
                            .alpha(0.9f)
                            .setDuration(80)
                            .start()
                    }

                    android.view.MotionEvent.ACTION_UP,
                    android.view.MotionEvent.ACTION_CANCEL -> {

                        v.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .alpha(1f)
                            .setDuration(80)
                            .start()
                    }
                }

                false
            }
        }

        cards.forEach {
            animateCard(it)
        }

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

        fun squishButton(button: Button) {

            button.setOnTouchListener { v, event ->

                when (event.action) {

                    android.view.MotionEvent.ACTION_DOWN -> {

                        v.animate()
                            .scaleX(0.92f)
                            .scaleY(0.92f)
                            .setDuration(80)
                            .start()
                    }

                    android.view.MotionEvent.ACTION_UP,
                    android.view.MotionEvent.ACTION_CANCEL -> {

                        v.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(80)
                            .start()
                    }
                }

                false
            }
        }

        squishButton(btnGuardar)
        squishButton(btnCancel)

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

            btnGuardar.animate()
                .rotation(5f)
                .setDuration(60)
                .withEndAction {

                    btnGuardar.animate()
                        .rotation(-5f)
                        .setDuration(60)
                        .withEndAction {

                            btnGuardar.animate()
                                .rotation(0f)
                                .setDuration(60)
                                .start()
                        }
                        .start()
                }
                .start()

            finish()
            overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
        }

        // CANCELAR
        btnCancel.setOnClickListener {
            finish()
            overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
        }
    }
}