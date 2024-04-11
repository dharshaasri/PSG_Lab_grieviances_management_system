package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BottomnavActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reportgriviances)

       // val myProfileTextView: TextView = findViewById(R.id.my_profile)
        //val labsTextView: TextView = findViewById(R.id.labs)
        val reportGrievanceTextView = findViewById<TextView>(R.id.report_grievance)
        //val viewGrievanceTextView: TextView = findViewById(R.id.view_grievance)

        // Click listeners for each TextView
//        myProfileTextView.setOnClickListener {
//            startActivity(Intent(this, MyProfileActivity::class.java))
//        }
//
//        labsTextView.setOnClickListener {
//            startActivity(Intent(this, LabsActivity::class.java))
//        }

        reportGrievanceTextView.setOnClickListener {
            startActivity(Intent(this, ReportGrievanceActivity::class.java))
        }

//        viewGrievanceTextView.setOnClickListener {
//            startActivity(Intent(this, ViewGrievanceActivity::class.java))
//        }
    }
}
