package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.BottomnavActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        // Include Navigation Bar
        includeNavigationBar()

        // Include Bottom Bar
        includeBottomBar()

        // Click listeners for lab cards
        setupClickListeners()

        val username = intent.getStringExtra("username")
        if (username != null) {
            // Use the username as needed
            // For example, display it in a TextView
            val usernameTextView = findViewById<TextView>(R.id.welcome_user_textview)
            usernameTextView.text = "Welcome, $username"
        }
    }

    private fun includeNavigationBar() {
        // Your code to include the navigation bar goes here
        // For example:
        MenuActivity()
    }

    private fun includeBottomBar() {
        // Start the BottomnavActivity
        //startActivity(Intent(this, BottomnavActivity::class.java))
        BottomnavActivity()
    }

    private fun setupClickListeners() {
        val cclab = findViewById<androidx.cardview.widget.CardView>(R.id.cc_lab_card)
        val islab = findViewById<androidx.cardview.widget.CardView>(R.id.is_lab_card)
        val projectlab = findViewById<androidx.cardview.widget.CardView>(R.id.project_lab_card)

        // Click listener for CC Lab Card
        cclab.setOnClickListener {
            // Start new activity for CC Lab
            val intent = Intent(this, CCLabActivity::class.java)
            intent.putExtra("username", intent.getStringExtra("username"))
            startActivity(intent)
        }

        // Click listener for Project Lab Card
        projectlab.setOnClickListener {
            // Start new activity for Project Lab
            val intent = Intent(this, ProjectLabActivity::class.java)
            intent.putExtra("username", intent.getStringExtra("username"))
            startActivity(intent)
        }

        // Click listener for Software Lab Card
        islab.setOnClickListener {
            // Start new activity for Software Lab
            val intent = Intent(this, ReportGrievanceActivity::class.java)
            intent.putExtra("username", intent.getStringExtra("username"))
            startActivity(intent)
        }
    }
}
