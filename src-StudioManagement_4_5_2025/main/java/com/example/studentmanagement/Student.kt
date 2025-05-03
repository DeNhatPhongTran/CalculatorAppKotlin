package com.example.studentmanagement

data class Student(
    var name: String,
    var studentId: String,
    var email: String,
    var phone: String
) : java.io.Serializable