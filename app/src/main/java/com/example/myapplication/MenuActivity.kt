package com.example.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

open class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_my_profile -> {
                // Handle My Profile click
                true
            }
            R.id.nav_report_grievances -> {
                // Handle Report Grievances click
                true
            }
            R.id.nav_view_grievances -> {
                // Handle View Grievances click
                true
            }
            R.id.nav_logout -> {
                // Handle Logout click
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
