package com.example.studentmanagement

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddEditStudentActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etId: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var btnSave: Button

    private var pos: Int = -1
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_student)

        etName = findViewById(R.id.etName)
        etId = findViewById(R.id.etId)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        btnSave = findViewById(R.id.btnSave)

        val student = intent.getSerializableExtra("student") as? Student
        if (student != null) {
            etName.setText(student.name)
            etId.setText(student.studentId)
            etEmail.setText(student.email)
            etPhone.setText(student.phone)
            pos = intent.getIntExtra("pos", -1)
            id = student.id
        }

        btnSave.setOnClickListener {
            val newStudent = Student(
                id = id,
                name = etName.text.toString(),
                studentId = etId.text.toString(),
                email = etEmail.text.toString(),
                phone = etPhone.text.toString()
            )
            val resultIntent = Intent().apply {
                putExtra("student", newStudent)
                putExtra("pos", pos)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
