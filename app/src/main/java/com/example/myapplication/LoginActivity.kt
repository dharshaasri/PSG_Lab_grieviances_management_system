package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {


    private lateinit var loginButton: Button
    private lateinit var usernameEmailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var errorMessageTextView: TextView
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton = findViewById(R.id.login_button)
        usernameEmailEditText = findViewById(R.id.username_email_edittext)
        passwordEditText = findViewById(R.id.password_edittext)
        errorMessageTextView = findViewById(R.id.error_message_textview)
        firebaseAuth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            Log.d("LoginActivity", "Login button clicked")
            val usernameOrEmail = usernameEmailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Check if both fields are not empty
            if (usernameOrEmail.isNotEmpty() && password.isNotEmpty()) {
                // Call Firebase signInWithEmailAndPassword method for authentication
                firebaseAuth.signInWithEmailAndPassword(usernameOrEmail, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Login successful, navigate to the next screen
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish() // Finish current activity to prevent user from coming back to login screen using back button
                        } else {
                            // If sign in fails, display a message to the user.
                            errorMessageTextView.text = "Authentication failed: ${task.exception?.message}"
                            errorMessageTextView.visibility = TextView.VISIBLE
                        }
                    }
            } else {
                // Display error message if fields are empty
                errorMessageTextView.text = "Please enter username/email and password"
                errorMessageTextView.visibility = TextView.VISIBLE
            }
        }
    }
}
