// Grievance.kt

package com.example.myapplication

data class Grievance(
    val type: String?,
    val lab: String?,
    val systemNo: String?,
    val description: String?
){
    fun toFormattedString(): String {
        return "Grievance Type: $type\nLab: $lab\nSystem No: $systemNo\nDescription: $description"
    }
}
