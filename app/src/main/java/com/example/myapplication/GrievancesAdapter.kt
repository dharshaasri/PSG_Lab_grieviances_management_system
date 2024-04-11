// GrievancesAdapter.kt

package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GrievancesAdapter(private val context: Context, private val grievancesList: List<Grievance>) :
    RecyclerView.Adapter<GrievancesAdapter.GrievanceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrievanceViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_grievance, parent, false)
        return GrievanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrievanceViewHolder, position: Int) {
        val grievance = grievancesList[position]
        holder.bind(grievance)
    }

    override fun getItemCount(): Int {
        return grievancesList.size
    }

    inner class GrievanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewGrievance: TextView = itemView.findViewById(R.id.textViewGrievance)

        fun bind(grievance: Grievance) {
            val formattedString = "Grievance type:${grievance.type}\nLab:${grievance.lab}\nSystem No: ${grievance.systemNo}\nDescription:${grievance.description}"
            textViewGrievance.text = formattedString
        }
    }
}
