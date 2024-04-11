import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ReportGrievanceFragment : Fragment() {

    private lateinit var grievancesRef: DatabaseReference
    private lateinit var username: String
    private lateinit var email: String
    private lateinit var className: String
    private lateinit var rollNumber: String// Variable to hold the username

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_report_grievance, container, false)
        val database = FirebaseDatabase.getInstance()
        grievancesRef = database.reference.child("grievances")

        // Retrieve the username from the intent extras
        // Retrieve the username from the intent extras
        username = requireActivity().intent.getStringExtra("username")?: "Unknown"
        email = requireActivity().intent.getStringExtra("email") ?.replace(".", "_") ?: "DefaultEmail"
        className = requireActivity().intent.getStringExtra("class") ?: "DefaultClass"
        rollNumber = requireActivity().intent.getStringExtra("rollNumber") ?: "DefaultRollnumber"

        Log.d("ReportGrievanceFragment", "Username: $username")
        Log.d("ReportGrievanceFragment", "Email: $email")
        Log.d("ReportGrievanceFragment", "Class Name: $className")
        Log.d("ReportGrievanceFragment", "Roll Number: $rollNumber")

        if (rollNumber != null && username!=null && className!=null&&email!=null) {
            // Use the username as needed
            // For example, display it in a TextView
            val usernameTextView = view.findViewById<TextView>(R.id.username_text)
            usernameTextView.text = "$username"
            val rollNumberTextView = view.findViewById<TextView>(R.id.roll_number_text)
            rollNumberTextView.text = "$rollNumber"
            val classNameTextView = view.findViewById<TextView>(R.id.class_text)
            classNameTextView.text = "$className"
            val emailTextView = view.findViewById<TextView>(R.id.email_text)
            emailTextView.text = "$email"
        }

        val grievanceEditText: EditText = view.findViewById(R.id.grievance_edit_text)
        val typeSpinner: Spinner = view.findViewById(R.id.type_spinner)
        val labEditText: EditText = view.findViewById(R.id.lab_edit_text)
        val systemNoEditText: EditText = view.findViewById(R.id.system_no_edit_text)
        val descriptionEditText: EditText = view.findViewById(R.id.description_edit_text)
        val submitButton: Button = view.findViewById(R.id.submit_button)

        // Spinner (dropdown) for selecting type
        ArrayAdapter.createFromResource(
            requireContext(),
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
            val username = requireActivity().intent.getStringExtra("username") ?: "Unknown"
            val email = requireActivity().intent.getStringExtra("email") ?: "DefaultEmail"
            val className = requireActivity().intent.getStringExtra("class") ?: "DefaultClass"
            val rollNumber = requireActivity().intent.getStringExtra("rollNumber") ?: "DefaultRollNumber"

            // Saving data to Firebase using the passed username
            val grievanceData = HashMap<String, Any>()
            grievanceData["grievance"] = grievance
            grievanceData["type"] = type
            grievanceData["lab"] = lab
            grievanceData["systemNo"] = systemNo
            grievanceData["description"] = description
            grievanceData["username"] = username // Include username
            grievanceData["email"] = email // Include email
            grievanceData["className"] = className // Include className
            grievanceData["rollNumber"] = rollNumber // Include rollNumber
            Log.d("Data stored","Data stored $username")

            // Store grievance under the username
            grievancesRef.child(rollNumber).push().setValue(grievanceData)
                .addOnCompleteListener { task ->
                    Log.d("Data stored","setting$username")
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Grievance submitted successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to submit grievance",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
        return view
    }
}
