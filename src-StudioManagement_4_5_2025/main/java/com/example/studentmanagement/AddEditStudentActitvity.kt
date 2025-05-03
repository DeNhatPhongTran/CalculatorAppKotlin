package com.example.studentmanagement

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddEditStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_student)

        val etName = findViewById<EditText>(R.id.etName)
        val etId = findViewById<EditText>(R.id.etId)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val btnSave = findViewById<Button>(R.id.btnSave)

        var pos = -1
        val student = intent.getSerializableExtra("student") as? Student
        if (student != null) {
            etName.setText(student.name)
            etId.setText(student.studentId)
            etEmail.setText(student.email)
            etPhone.setText(student.phone)
            pos = intent.getIntExtra("pos", -1)
        }

        btnSave.setOnClickListener {
            val result = Student(
                etName.text.toString(),
                etId.text.toString(),
                etEmail.text.toString(),
                etPhone.text.toString()
            )
            val data = Intent().apply {
                putExtra("student", result)
                putExtra("pos", pos)
            }
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }
}