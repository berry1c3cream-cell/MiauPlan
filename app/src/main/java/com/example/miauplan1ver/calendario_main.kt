package com.example.miauplan1ver

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.ImageView

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

        for (i in 1..31) {
            val resID = resources.getIdentifier("day$i", "id", packageName)
            val dayView = findViewById<TextView>(resID)

            dayView?.setOnClickListener {
                dayView.setBackgroundResource(R.drawable.circle_day)

                val intent = Intent(this, EventoDiaActivity::class.java)
                intent.putExtra("DIA", i)
                startActivity(intent)
            }
        }

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val nombre = intent.getStringExtra("NOMBRE_GATO") ?: "tu gato"

        val tvTitulo = findViewById<TextView>(R.id.tvTitulo)
        tvTitulo.text = "¿Qué hizo $nombre hoy?"

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()

        for (i in 1..31) {
            val resID = resources.getIdentifier("day$i", "id", packageName)
            val dayView = findViewById<TextView>(resID)

            dayView?.setBackgroundResource(0)
        }
    }
}