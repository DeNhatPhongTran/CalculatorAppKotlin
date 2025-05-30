package com.example.studentmanagement

import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val students: MutableList<Student>,
    private val onAction: (position: Int, action: Action) -> Unit
) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    enum class Action { EDIT, DELETE, CALL, EMAIL }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.textName)
        val txtId: TextView = itemView.findViewById(R.id.textMSSV)
        private val btnMenu: ImageButton = itemView.findViewById(R.id.buttonMenu)

        init {
            btnMenu.setOnClickListener {
                val popup = PopupMenu(itemView.context, btnMenu)
                popup.menuInflater.inflate(R.menu.menu_student_item, popup.menu)
                popup.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_edit -> onAction(adapterPosition, Action.EDIT)
                        R.id.menu_delete -> onAction(adapterPosition, Action.DELETE)
                        R.id.menu_call -> onAction(adapterPosition, Action.CALL)
                        R.id.menu_email -> onAction(adapterPosition, Action.EMAIL)
                    }
                    true
                }
                popup.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = students[position]
        holder.txtName.text = student.name
        holder.txtId.text = student.studentId
    }

    override fun getItemCount(): Int = students.size

    fun addStudent(student: Student) {
        students.add(0, student)
        notifyItemInserted(0)
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