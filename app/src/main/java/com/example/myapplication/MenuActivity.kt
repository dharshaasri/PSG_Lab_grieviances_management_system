package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_navigation) // Set the content view to your main layout file

        // Find the menu button and menu bar views

        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.menu_navigation, null)

        val menuButton = findViewById<ImageButton>(R.id.menu_button)
        val menuBar = findViewById<LinearLayout>(R.id.menu_bar)

        // Set click listener for the menu button
        menuButton.setOnClickListener {
            // Toggle visibility of the menu bar
            if (menuBar.visibility == View.VISIBLE) {
                menuBar.visibility = View.GONE
            } else {
                menuBar.visibility = View.VISIBLE
            }
        }
        // Find the logout button by its ID
        val logoutButton: ImageButton = findViewById(R.id.logout_button) // Other code...
        // Optionally, you can also set click listeners for the menu items inside the menu bar
        val myProfileTextView = findViewById<TextView>(R.id.my_profile)
        val labsTextView = findViewById<TextView>(R.id.labs)
        val reportGrievanceTextView = findViewById<TextView>(R.id.report_grievance)
        val viewGrievanceTextView = findViewById<TextView>(R.id.view_grievance)

        menuButton.setOnClickListener {
            // Show/hide the menu bar
           // toggleMenuBarVisibility()
        }

        // Handle menu item clicks
        myProfileTextView.setOnClickListener {
            // Handle click on My Profile
            // For example, open MyProfileActivity
            val intent = Intent(this, MyProfileActivity::class.java)
            startActivity(intent)
        }

        labsTextView.setOnClickListener {
            // Handle click on Labs
            // For example, open LabsActivity
            val intent = Intent(this, LabsActivity::class.java)
            startActivity(intent)
        }

        reportGrievanceTextView.setOnClickListener {
            // Handle click on Report Grievance
            // For example, open ReportGrievanceActivity
            val intent = Intent(this, ReportGrievanceActivity::class.java)
            startActivity(intent)
        }

        viewGrievanceTextView.setOnClickListener {
            // Handle click on View Grievance
            // For example, open ViewGrievanceActivity
            val intent = Intent(this, ViewGrievanceActivity::class.java)
            startActivity(intent)
        }
        logoutButton.setOnClickListener {
            logout() // Call the logout function when the button is clicked
        }
    }
    private fun logout() {
        // Clear any user session or authentication token
        // For example, you can use SharedPreferences to clear user data
        // Replace this with your actual logout implementation
        val sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // Navigate to the login activity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Close the current activity
    }

}
