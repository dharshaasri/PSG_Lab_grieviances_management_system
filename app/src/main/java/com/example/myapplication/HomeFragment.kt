package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        setupClickListeners(view)

        val rollNumber = requireActivity().intent.getStringExtra("rollNumber")
        if (rollNumber != null) {
            // Use the username as needed
            // For example, display it in a TextView
            val usernameTextView = view.findViewById<TextView>(R.id.welcome_user_textview)
            usernameTextView.text = "Welcome, $rollNumber"
        }
        return view
    }

    private fun setupClickListeners(view: View) {
        val cclab = view.findViewById<CardView>(R.id.cc_lab_card)
        val islab = view.findViewById<CardView>(R.id.is_lab_card)
        val projectlab = view.findViewById<CardView>(R.id.project_lab_card)

        // Click listener for CC Lab Card
        cclab.setOnClickListener {
            // Start new activity for CC Lab
            val intent = Intent(requireActivity(), CCLabActivity::class.java)
            intent.putExtra("username", requireActivity().intent.getStringExtra("username"))
            startActivity(intent)
        }

        // Click listener for Project Lab Card
        projectlab.setOnClickListener {
            // Start new activity for Project Lab
            val intent = Intent(requireActivity(), ProjectLabActivity::class.java)
            intent.putExtra("username", requireActivity().intent.getStringExtra("username"))
            startActivity(intent)
        }

        // Click listener for Software Lab Card
        islab.setOnClickListener {
            // Start new activity for Software Lab
            val intent = Intent(requireActivity(), ISLabActivity::class.java)
            intent.putExtra("username", requireActivity().intent.getStringExtra("username"))
            startActivity(intent)
        }
    }
}
