package com.example.myapplication

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewGrievanceFragment : Fragment() {

    private lateinit var grievancesRecyclerView: RecyclerView
    private lateinit var grievancesAdapter: GrievancesAdapter
    private lateinit var grievancesList: MutableList<Grievance>
    private lateinit var loggedInUserRollNumber: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_grievance, container, false)

        // Initialize RecyclerView and adapter
        grievancesRecyclerView = view.findViewById(R.id.grievances_recycler_view)
        grievancesList = mutableListOf()
        grievancesAdapter = GrievancesAdapter(requireContext(), grievancesList)
        grievancesRecyclerView.adapter = grievancesAdapter
        grievancesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Retrieve user's roll number
        loggedInUserRollNumber = requireActivity().intent.getStringExtra("rollNumber") ?: "DefaultRollNumber"

        // Retrieve grievances from Firebase and populate RecyclerView
        retrieveGrievances()

        return view
    }

    private fun retrieveGrievances() {
        val database = FirebaseDatabase.getInstance()
        val grievancesRef = database.getReference("grievances").child(loggedInUserRollNumber)

        // Listen for data changes and update RecyclerView accordingly
        grievancesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                grievancesList.clear()
                for (grievanceSnapshot in snapshot.children) {
                    val type = grievanceSnapshot.child("type").getValue(String::class.java)
                    val lab = grievanceSnapshot.child("lab").getValue(String::class.java)
                    val systemNo = grievanceSnapshot.child("systemNo").getValue(String::class.java)
                    val description = grievanceSnapshot.child("description").getValue(String::class.java)

                    // Create Grievance object and add it to the list
                    val grievance = Grievance(type, lab, systemNo, description)
                    grievancesList.add(grievance)
                }
                grievancesAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event
                Toast.makeText(requireContext(), "Failed to retrieve grievances", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
