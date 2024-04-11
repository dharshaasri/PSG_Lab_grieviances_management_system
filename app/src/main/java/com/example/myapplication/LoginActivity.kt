package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var usernameEmailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var errorMessageTextView: TextView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton = findViewById(R.id.login_button)
        usernameEmailEditText = findViewById(R.id.username_email_edittext)
        passwordEditText = findViewById(R.id.password_edittext)
        errorMessageTextView = findViewById(R.id.error_message_textview)
        databaseReference = FirebaseDatabase.getInstance().getReference("students")

        loginButton.setOnClickListener {
            val usernameOrEmail = usernameEmailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Check if both fields are not empty
            if (usernameOrEmail.isNotEmpty() && password.isNotEmpty()) {
                // Query the database to find a student with the provided email in "g1"
                databaseReference.child("g1").orderByChild("email").equalTo(usernameOrEmail)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            for (studentSnapshot in dataSnapshot.children) {
                                val student = studentSnapshot.getValue(Student::class.java)
                                if (student != null && student.email == usernameOrEmail && student.password == hashString(password)) {
                                    // Login successful, navigate to the next screen
                                    val intent = Intent(this@LoginActivity, BottomNav::class.java)
                                    intent.putExtra("username", student.name)
                                    intent.putExtra("email", student.email)
                                    intent.putExtra("class", student.className)
                                    intent.putExtra("rollNumber", student.rollNumber)// Pass the username (email) as an extra
                                    startActivity(intent)
                                    finish() // Finish current activity to prevent user from coming back to login screen using back button
                                    return
                                }
                            }
                            // If no matching student found in "g1", query "g2"
                            checkG2(usernameOrEmail, password)
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.e("LoginActivity", "Database query cancelled: ${databaseError.message}")
                        }
                    })
            } else {
                // Display error message if fields are empty
                errorMessageTextView.text = "Please enter username/email and password"
                errorMessageTextView.visibility = TextView.VISIBLE
            }
        }
    }

    private fun checkG2(usernameOrEmail: String, password: String) {
        // Query the database to find a student with the provided email in "g2"
        databaseReference.child("g2").orderByChild("email").equalTo(usernameOrEmail)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (studentSnapshot in dataSnapshot.children) {
                        val student = studentSnapshot.getValue(Student::class.java)
                        if (student != null && student.email == usernameOrEmail && student.password == hashString(password)) {
                            // Login successful, navigate to the next screen
                            val intent = Intent(this@LoginActivity, HomeFragment::class.java)
                            intent.putExtra("username", student.email) // Pass the username (email) as an extra
                            startActivity(intent)
                            finish() // Finish current activity to prevent user from coming back to login screen using back button
                            return
                        }
                    }
                    // If no matching student found in "g2" either, display error message
                    errorMessageTextView.text = "Invalid username/email or password"
                    errorMessageTextView.visibility = TextView.VISIBLE
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("LoginActivity", "Database query cancelled: ${databaseError.message}")
                }
            })
    }

    private fun hashString(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
