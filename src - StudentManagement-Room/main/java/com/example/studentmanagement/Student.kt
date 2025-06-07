package com.example.studentmanagement

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val name: String,
    val studentId: String,
    val email: String,
    val phone: String
) : Serializable