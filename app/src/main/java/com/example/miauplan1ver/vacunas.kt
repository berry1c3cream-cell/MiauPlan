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
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView

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

        findViewById<View>(R.id.main).alpha = 0f

        findViewById<View>(R.id.main).animate()
            .alpha(1f)
            .setDuration(400)
            .start()

        val dia = intent.getIntExtra("DIA", 1)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnCancel = findViewById<Button>(R.id.btnCancelar)

        val claveRabia = "rabia_$dia"
        val claveTriple = "triple_$dia"
        val claveLeucemia = "leucemia_$dia"
        val claveBordetella = "bordetella_$dia"

        val cardTriple = findViewById<CardView>(R.id.cardTriple)
        val cardRabia = findViewById<CardView>(R.id.cardRabia)
        val cardLeucemia = findViewById<CardView>(R.id.cardLeucemia)
        val cardBordetella = findViewById<CardView>(R.id.cardBordetella)

        val cards = listOf(
            cardTriple,
            cardRabia,
            cardLeucemia,
            cardBordetella
        )

        cards.forEachIndexed { index, card ->

            card.alpha = 0f
            card.translationY = 40f

            card.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay((index * 90).toLong())
                .setDuration(350)
                .start()
        }

        cards.forEach { card ->

            card.setOnTouchListener { v, event ->

                when (event.action) {

                    MotionEvent.ACTION_DOWN -> {
                        v.animate()
                            .scaleX(0.97f)
                            .scaleY(0.97f)
                            .setDuration(90)
                            .start()
                    }

                    MotionEvent.ACTION_UP,
                    MotionEvent.ACTION_CANCEL -> {

                        v.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(90)
                            .start()
                    }
                }

                false
            }
        }

        checkTriple.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {

                cardTriple.cardElevation = 12f

            } else {

                cardTriple.cardElevation = 2f
            }
        }

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

        val checks = listOf(
            checkRabia,
            checkTriple,
            checkLeucemia,
            checkBordetella
        )

        checks.forEach { check ->

            check.setOnCheckedChangeListener { buttonView, isChecked ->

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

        // GUARDAR DATOS
        btnGuardar.setOnClickListener {

            btnGuardar.animate()
                .scaleX(0.92f)
                .scaleY(0.92f)
                .setDuration(80)
                .withEndAction {

                    btnGuardar.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(80)
                        .start()
                }
                .start()

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

            btnCancel.animate()
                .rotation(-4f)
                .setDuration(60)
                .withEndAction {

                    btnCancel.animate()
                        .rotation(4f)
                        .setDuration(60)
                        .withEndAction {

                            btnCancel.animate()
                                .rotation(0f)
                                .setDuration(60)
                                .start()

                            finish()
                        }
                        .start()
                }
                .start()
        }
    }
}