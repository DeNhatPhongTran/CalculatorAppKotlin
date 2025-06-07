package com.example.studentmanagement

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: StudentAdapter
    private lateinit var db: StudentDatabase
    private val list = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = StudentDatabase.getInstance(this)
        adapter = StudentAdapter(list) { pos, action ->
            when (action) {
                StudentAdapter.Action.EDIT -> openEdit(pos)
                StudentAdapter.Action.DELETE -> confirmDelete(pos)
                StudentAdapter.Action.CALL -> call(list[pos].phone)
                StudentAdapter.Action.EMAIL -> email(list[pos].email)
            }
        }

        val rv = findViewById<RecyclerView>(R.id.recyclerViewStudents)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        findViewById<View>(R.id.fabAdd).setOnClickListener {
            startActivityForResult(Intent(this, AddEditStudentActivity::class.java), REQUEST_ADD)
        }

        loadData()
    }

    private fun loadData() {
        lifecycleScope.launch {
            val data = db.studentDao().getAll()
            list.clear()
            list.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val student = data.getSerializableExtra("student") as Student
            val pos = data.getIntExtra("pos", -1)
            lifecycleScope.launch {
                if (requestCode == REQUEST_ADD) {
                    val id = db.studentDao().insert(student).toInt()
                    student.id = id
                    adapter.addStudent(student)
                } else if (requestCode == REQUEST_EDIT && pos >= 0) {
                    db.studentDao().update(student)
                    adapter.updateStudent(pos, student)
                }
            }
        }
    }

    private fun openEdit(pos: Int) {
        val intent = Intent(this, AddEditStudentActivity::class.java)
        intent.putExtra("student", list[pos])
        intent.putExtra("pos", pos)
        startActivityForResult(intent, REQUEST_EDIT)
    }

    private fun confirmDelete(pos: Int) {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận")
            .setMessage("Bạn có chắc muốn xóa không?")
            .setPositiveButton("Xóa") { _, _ ->
                lifecycleScope.launch {
                    db.studentDao().delete(list[pos])
                    adapter.removeStudent(pos)
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun call(phone: String) {
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone")))
    }

    private fun email(address: String) {
        startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$address")))
    }

    companion object {
        const val REQUEST_ADD = 1
        const val REQUEST_EDIT = 2
    }
}