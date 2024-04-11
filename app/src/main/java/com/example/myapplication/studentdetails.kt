package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.security.MessageDigest

class studentdetails : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_student)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val etName = findViewById<EditText>(R.id.etName)
        val etRollNumber = findViewById<EditText>(R.id.etRollNumber)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etDOB = findViewById<EditText>(R.id.etDOB)
        val spinnerClass = findViewById<Spinner>(R.id.spinnerClass)

        val classOptions = arrayOf("g1", "g2")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, classOptions)
        spinnerClass.adapter = adapter

        btnSubmit.setOnClickListener {
            val name = etName.text.toString()
            val rollNumber = etRollNumber.text.toString()
            val email = etEmail.text.toString()
            val selectedClass = classOptions[spinnerClass.selectedItemPosition]
            val dob = etDOB.text.toString()

            // Hash the password using SHA-256
            val hashedPassword = hashString(rollNumber)

            // Create user with email and password
            mAuth.createUserWithEmailAndPassword(email, hashedPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = mAuth.currentUser?.uid
                        val student = Student(name, email, selectedClass, dob, rollNumber, hashedPassword)
                        userId?.let { uid ->
                            mDatabase.child("students").child(selectedClass).child(uid).setValue(student)
                        }
                    } else {
                        // Handle errors
                        Log.e("StudentDetailsActivity", "Error creating user", task.exception)
                    }
                }
        }
    }

    private fun hashString(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}

data class Student(
    val name: String = "",
    val email: String = "",
    val className: String = "",
    val dob: String = "",
    val rollNumber: String = "", // Add rollNumber property
    val password: String = "" // Add password property
)
