package com.example.miauplan1ver

import android.content.Intent
import android.os.Bundle
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

        val btnSave = findViewById<com.google.android.material.button.MaterialButton>(R.id.btnSave)
        val btnCancel = findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCancel)

        // SAVE → nueva pantalla
        btnSave.setOnClickListener {
            val intent = Intent(this, CalendarioMainActivity::class.java)
            startActivity(intent)
        }

        // CANCEL → regresar a la pantalla anterior
        btnCancel.setOnClickListener {
            finish()
        }
    }
}