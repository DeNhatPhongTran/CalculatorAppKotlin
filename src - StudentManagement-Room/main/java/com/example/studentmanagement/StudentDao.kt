package com.example.studentmanagement

import androidx.room.*

@Dao
interface StudentDao {
    @Query("SELECT * FROM students")
    suspend fun getAll(): List<Student>

    @Insert
    suspend fun insert(student: Student): Long

    @Update
    suspend fun update(student: Student)

    @Delete
    suspend fun delete(student: Student)
}