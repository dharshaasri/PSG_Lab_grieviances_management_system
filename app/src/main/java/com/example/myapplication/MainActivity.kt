package com.example.myapplication
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)

        val cclab = findViewById<androidx.cardview.widget.CardView>(R.id.cc_lab_card)
        val islab = findViewById<androidx.cardview.widget.CardView>(R.id.is_lab_card)
        val projectlab = findViewById<androidx.cardview.widget.CardView>(R.id.project_lab_card)

        // Click listener for CC Lab Card
        cclab.setOnClickListener {
            // Start new activity for CC Lab
            val intent = Intent(this, CCLabActivity::class.java)
            startActivity(intent)
        }

        // Click listener for Project Lab Card
        projectlab.setOnClickListener {
            // Start new activity for Project Lab
            val intent = Intent(this, ProjectLabActivity::class.java)
            startActivity(intent)
        }

        // Click listener for Software Lab Card
        islab.setOnClickListener {
            // Start new activity for Software Lab
            val intent = Intent(this, ISLabActivity::class.java)
            startActivity(intent)
        }
    }
}
