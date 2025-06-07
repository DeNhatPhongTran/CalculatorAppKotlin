package com.example.studentmanagement

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class StudentAdapter(
    private val students: MutableList<Student>,
    private val onAction: (Int, Action) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    enum class Action { EDIT, DELETE, CALL, EMAIL }

    inner class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.textName)
        val mssv: TextView = view.findViewById(R.id.textMSSV)
        val buttonMenu: ImageButton = view.findViewById(R.id.buttonMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.name.text = student.name
        holder.mssv.text = student.studentId
        holder.buttonMenu.setOnClickListener {
            val popup = PopupMenu(it.context, it)
            popup.inflate(R.menu.menu_main)
            popup.setOnMenuItemClickListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.menu_edit -> onAction(position, Action.EDIT)
                    R.id.menu_delete -> onAction(position, Action.DELETE)
                    R.id.menu_call -> onAction(position, Action.CALL)
                    R.id.menu_email -> onAction(position, Action.EMAIL)
                }
                true
            }
            popup.show()
        }
    }

    override fun getItemCount() = students.size

    fun addStudent(student: Student) {
        students.add(student)
        notifyItemInserted(students.lastIndex)
    }

    fun updateStudent(pos: Int, student: Student) {
        students[pos] = student
        notifyItemChanged(pos)
    }

    fun removeStudent(pos: Int) {
        students.removeAt(pos)
        notifyItemRemoved(pos)
    }
}