package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewGrievanceActivity : Fragment() {

    private lateinit var grievancesTable: TableLayout
    private lateinit var grievancesRef: DatabaseReference
    private lateinit var loggedInUserRollNumber: String
    private var serialNumber = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view_grievance, container, false)
        grievancesTable = view.findViewById(R.id.grievances_table)

        val username = requireActivity().intent.getStringExtra("username") ?: "Unknown"
        val email = requireActivity().intent.getStringExtra("email") ?: "DefaultEmail"
        val className = requireActivity().intent.getStringExtra("class") ?: "DefaultClass"
        val rollNumber = requireActivity().intent.getStringExtra("rollNumber") ?: "DefaultRollNumber"
        val database = FirebaseDatabase.getInstance()
        grievancesRef = database.getReference("grievances")

        // Assuming the user is logged in and you have their roll number stored somewhere
        // Retrieve the logged-in user's roll number
        loggedInUserRollNumber = rollNumber // Example roll number, replace with actual logged-in user's roll number

        grievancesRef.child(loggedInUserRollNumber).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                grievancesTable.removeAllViews()
                serialNumber = 1

                for (grievanceSnapshot in snapshot.children) {
                    val type = grievanceSnapshot.child("type").getValue(String::class.java)
                    val lab = grievanceSnapshot.child("lab").getValue(String::class.java)
                    val systemNo = grievanceSnapshot.child("systemNo").getValue(String::class.java)
                    val description = grievanceSnapshot.child("description").getValue(String::class.java)

                    addGrievanceToTable(serialNumber++, type, lab, systemNo, description)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to retrieve grievances", Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }

    private fun addGrievanceToTable(serialNumber: Int, type: String?, lab: String?, systemNo: String?, description: String?) {
        val row = TableRow(requireContext())
        val params = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        row.layoutParams = params

        val textViewSerialNumber = TextView(requireContext())
        textViewSerialNumber.text = serialNumber.toString()
        textViewSerialNumber.setPadding(8, 8, 8, 8)
        row.addView(textViewSerialNumber)

        val textViewType = TextView(requireContext())
        textViewType.text = type
        textViewType.setPadding(8, 8, 8, 8)
        row.addView(textViewType)

        val textViewLab = TextView(requireContext())
        textViewLab.text = lab
        textViewLab.setPadding(8, 8, 8, 8)
        row.addView(textViewLab)

        val textViewSystemNo = TextView(requireContext())
        textViewSystemNo.text = systemNo
        textViewSystemNo.setPadding(8, 8, 8, 8)
        row.addView(textViewSystemNo)

        val textViewDescription = TextView(requireContext())
        textViewDescription.text = description
        textViewDescription.setPadding(8, 8, 8, 8)
        row.addView(textViewDescription)

        grievancesTable.addView(row, 0) // Add the row at index 0 to display new grievances on top
    }
}
