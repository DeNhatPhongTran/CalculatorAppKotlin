package com.example.studentmanagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class SinhVien(val hoTen: String, val mssv: String)

class SinhVienAdapter(private val danhSachSinhVien: MutableList<SinhVien>) :
    RecyclerView.Adapter<SinhVienAdapter.SinhVienViewHolder>() {

    inner class SinhVienViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewHoTen: TextView = itemView.findViewById(R.id.textViewHoTen)
        val textViewMSSV: TextView = itemView.findViewById(R.id.textViewMSSV)
        private val buttonXoa: Button = itemView.findViewById(R.id.buttonXoa)

        init {
            buttonXoa.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    danhSachSinhVien.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SinhVienViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sinhvien, parent, false)
        return SinhVienViewHolder(view)
    }

    override fun onBindViewHolder(holder: SinhVienViewHolder, position: Int) {
        val sinhVien = danhSachSinhVien[position]
        holder.textViewHoTen.text = sinhVien.hoTen
        holder.textViewMSSV.text = sinhVien.mssv
    }

    override fun getItemCount(): Int = danhSachSinhVien.size

    fun themSinhVien(sinhVien: SinhVien) {
        danhSachSinhVien.add(0, sinhVien)
        notifyItemInserted(0)
    }
}


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var sinhVienAdapter: SinhVienAdapter
    private val danhSachSinhVien = mutableListOf<SinhVien>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewStudents)
        sinhVienAdapter = SinhVienAdapter(danhSachSinhVien)
        recyclerView.adapter = sinhVienAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val buttonThem = findViewById<Button>(R.id.buttonThem)
        val editTextHoTen = findViewById<EditText>(R.id.editTextHoTen)
        val editTextMSSV = findViewById<EditText>(R.id.editTextMSSV)

        buttonThem.setOnClickListener {
            val hoTen = editTextHoTen.text.toString().trim()
            val mssv = editTextMSSV.text.toString().trim()
            if (hoTen.isNotEmpty() && mssv.isNotEmpty()) {
                val sinhVienMoi = SinhVien(hoTen, mssv)
                sinhVienAdapter.themSinhVien(sinhVienMoi)
                recyclerView.scrollToPosition(0)
                editTextHoTen.text.clear()
                editTextMSSV.text.clear()
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

