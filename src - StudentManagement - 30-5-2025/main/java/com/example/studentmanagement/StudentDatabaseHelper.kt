package com.example.studentmanagement

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "students.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE students (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                studentId TEXT,
                email TEXT,
                phone TEXT
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS students")
        onCreate(db)
    }

    fun insertStudent(student: Student): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", student.name)
            put("studentId", student.studentId)
            put("email", student.email)
            put("phone", student.phone)
        }
        return db.insert("students", null, values)
    }

    fun getAllStudents(): List<Student> {
        val list = mutableListOf<Student>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM students", null)
        while (cursor.moveToNext()) {
            list.add(
                Student(
                    id = cursor.getInt(0),
                    name = cursor.getString(1),
                    studentId = cursor.getString(2),
                    email = cursor.getString(3),
                    phone = cursor.getString(4)
                )
            )
        }
        cursor.close()
        return list
    }

    fun updateStudent(student: Student): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", student.name)
            put("studentId", student.studentId)
            put("email", student.email)
            put("phone", student.phone)
        }
        return db.update("students", values, "id=?", arrayOf(student.id.toString()))
    }

    fun deleteStudent(id: Int): Int {
        val db = writableDatabase
        return db.delete("students", "id=?", arrayOf(id.toString()))
    }
}