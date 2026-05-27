package com.example.miauplan1ver

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import android.view.View

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

        toggleGenero.addOnButtonCheckedListener { group, checkedId, isChecked ->

            if (isChecked) {

                val button = findViewById<View>(checkedId)

                button.animate()
                    .scaleX(1.08f)
                    .scaleY(1.08f)
                    .setDuration(100)
                    .withEndAction {
                        button.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100)
                            .start()
                    }
                    .start()
            }
        }

        etNombre.setOnFocusChangeListener { v, hasFocus ->

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

                btnSave.animate()
                    .rotation(6f)
                    .setDuration(60)
                    .withEndAction {
                        btnSave.animate()
                            .rotation(0f)
                            .setDuration(60)
                            .start()
                    }
                    .start()

                finish()

                overridePendingTransition(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )

            } else {
                etNombre.error = "Ponle nombre al michi!!"

                etNombre.animate()
                    .translationX(20f)
                    .setDuration(50)
                    .withEndAction {
                        etNombre.animate()
                            .translationX(-20f)
                            .setDuration(50)
                            .withEndAction {
                                etNombre.animate()
                                    .translationX(0f)
                                    .setDuration(50)
                                    .start()
                            }
                            .start()
                    }
                    .start()
            }

            fun animateButton(view: View) {

                view.setOnTouchListener { v, event ->

                    when (event.action) {

                        android.view.MotionEvent.ACTION_DOWN -> {
                            v.animate()
                                .scaleX(0.93f)
                                .scaleY(0.93f)
                                .setDuration(100)
                                .start()
                        }

                        android.view.MotionEvent.ACTION_UP,
                        android.view.MotionEvent.ACTION_CANCEL -> {

                            v.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(100)
                                .start()
                        }
                    }

                    false
                }
            }

            animateButton(btnSave)
            animateButton(btnCancel)
        }


        // CANCEL → regresar a la pantalla anterior
        btnCancel.setOnClickListener {

            fun animateButton(view: View) {

                view.setOnTouchListener { v, event ->

                    when (event.action) {

                        android.view.MotionEvent.ACTION_DOWN -> {
                            v.animate()
                                .scaleX(0.93f)
                                .scaleY(0.93f)
                                .setDuration(100)
                                .start()
                        }

                        android.view.MotionEvent.ACTION_UP,
                        android.view.MotionEvent.ACTION_CANCEL -> {

                            v.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(100)
                                .start()
                        }
                    }

                    false
                }
            }

            animateButton(btnSave)
            animateButton(btnCancel)

            finish()
        }

        val views = listOf(
            findViewById<View>(R.id.layoutNombre),
            findViewById<View>(R.id.toggleGenero),
            findViewById<View>(R.id.layoutEdad),
            findViewById<View>(R.id.layoutRaza),
            findViewById<View>(R.id.layoutPeso),
            btnSave,
            btnCancel
        )

        views.forEachIndexed { index, view ->

            view.alpha = 0f
            view.translationY = 50f

            view.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay((index * 80).toLong())
                .setDuration(350)
                .start()
        }
    }
}