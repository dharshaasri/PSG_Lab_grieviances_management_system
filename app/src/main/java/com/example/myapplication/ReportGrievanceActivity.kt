package com.example.myapplication

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ReportGrievanceActivity : AppCompatActivity() {

    private lateinit var grievancesRef: DatabaseReference
    private lateinit var currentUser: FirebaseUser // To store the current logged-in user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reportgriviances)

        // Firebase initialization
        val database = FirebaseDatabase.getInstance()
        grievancesRef = database.getReference("grievances")

        // Get the current logged-in user asynchronously
        FirebaseAuth.getInstance().currentUser?.let { user ->
            currentUser = user

            val grievanceEditText: EditText = findViewById(R.id.grievance_edit_text)
            val typeSpinner: Spinner = findViewById(R.id.type_spinner)
            val labEditText: EditText = findViewById(R.id.lab_edit_text)
            val systemNoEditText: EditText = findViewById(R.id.system_no_edit_text)
            val descriptionEditText: EditText = findViewById(R.id.description_edit_text)
            val submitButton: Button = findViewById(R.id.submit_button)

            // Spinner (dropdown) for selecting type
            ArrayAdapter.createFromResource(
                this,
                R.array.types_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                typeSpinner.adapter = adapter
            }

            submitButton.setOnClickListener {
                val grievance = grievanceEditText.text.toString()
                val type = typeSpinner.selectedItem.toString()
                val lab = labEditText.text.toString()
                val systemNo = systemNoEditText.text.toString()
                val description = descriptionEditText.text.toString()

                // Check if the current user is null
                if (currentUser != null) {
                    // Get user email, if null use a default value
                    val userEmail = currentUser.email ?: "Unknown"

                    // Saving data to Firebase
                    val grievanceData = HashMap<String, Any>()
                    grievanceData["grievance"] = grievance
                    grievanceData["type"] = type
                    grievanceData["lab"] = lab
                    grievanceData["systemNo"] = systemNo
                    grievanceData["description"] = description
                    grievanceData["userEmail"] = userEmail // Include user's email

                    // Store grievance under the current user's UID
                    val userGrievancesRef = grievancesRef.child(currentUser.uid)
                    userGrievancesRef.push().setValue(grievanceData)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Grievance submitted successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Failed to submit grievance",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        this,
                        "User not authenticated",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
