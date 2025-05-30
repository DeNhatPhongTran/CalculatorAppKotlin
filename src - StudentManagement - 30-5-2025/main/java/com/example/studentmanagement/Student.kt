package com.example.studentmanagement

data class Student(
    var id: Int = 0,
    var name: String,
    var studentId: String,
    var email: String,
    var phone: String
) : java.io.Serializable