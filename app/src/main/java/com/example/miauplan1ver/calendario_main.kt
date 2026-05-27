package com.example.miauplan1ver

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.google.android.material.card.MaterialCardView

class CalendarioMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.calendario_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefs = getSharedPreferences("GatosDB", MODE_PRIVATE)
        val nombre = prefs.getString("nombre", "tu gato")
        val tipCard = findViewById<View>(R.id.tipCard)

        tipCard.animate()
            .translationYBy(-6f)
            .setDuration(1400)
            .withEndAction {

                tipCard.animate()
                    .translationYBy(6f)
                    .setDuration(1400)
                    .start()
            }
            .start()

        val calendarCard = findViewById<MaterialCardView>(R.id.calendarCard)

        calendarCard.alpha = 0f
        calendarCard.translationY = 80f

        calendarCard.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(500)
            .start()

        val tvTitulo = findViewById<TextView>(R.id.tvTitulo)
        tvTitulo.text = "¿Qué hizo $nombre hoy?"

        tvTitulo.translationX = -100f
        tvTitulo.alpha = 0f

        tvTitulo.animate()
            .translationX(0f)
            .alpha(1f)
            .setDuration(500)
            .start()

        for (i in 1..31) {

            val resID = resources.getIdentifier("day$i", "id", packageName)
            val dayView = findViewById<TextView>(resID)

            dayView?.setOnTouchListener { v, event ->

                when (event.action) {

                    android.view.MotionEvent.ACTION_DOWN -> {

                        v.animate()
                            .scaleX(0.85f)
                            .scaleY(0.85f)
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

            dayView?.setOnClickListener {

                val intent = Intent(this, EventoDiaActivity::class.java)
                intent.putExtra("DIA", i)
                startActivity(intent)

                overridePendingTransition(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
            }

            dayView?.animate()
                ?.rotation(8f)
                ?.setDuration(60)
                ?.withEndAction {

                    dayView.animate()
                        .rotation(0f)
                        .setDuration(60)
                        .start()
                }
                ?.start()
        }

        val btnBack = findViewById<ImageView>(R.id.btnBack)

        btnBack.setOnTouchListener { v, event ->

            when (event.action) {

                android.view.MotionEvent.ACTION_DOWN -> {
                    v.animate()
                        .rotation(-15f)
                        .scaleX(0.9f)
                        .scaleY(0.9f)
                        .setDuration(100)
                        .start()
                }

                android.view.MotionEvent.ACTION_UP,
                android.view.MotionEvent.ACTION_CANCEL -> {

                    v.animate()
                        .rotation(0f)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                        .start()
                }
            }

            false
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        val prefs = getSharedPreferences("EventosDB", MODE_PRIVATE)

        for (i in 1..31) {

            val resID = resources.getIdentifier("day$i", "id", packageName)
            val dayView = findViewById<TextView>(resID)

            val evento = prefs.getString("evento_$i", "")

            if (!evento.isNullOrEmpty()) {

                dayView?.setBackgroundResource(R.drawable.circle_day)

                dayView?.animate()
                    ?.scaleX(1.1f)
                    ?.scaleY(1.1f)
                    ?.setDuration(150)
                    ?.withEndAction {

                        dayView.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(150)
                            .start()
                    }
                    ?.start()

            } else {

                dayView?.setBackgroundResource(0)
            }
        }
    }
}