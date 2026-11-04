package com.example.mobileadvapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileadvapp.data.local.AppDatabase
import com.example.mobileadvapp.databinding.ActivityStudentBinding
import kotlinx.coroutines.launch

class StudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentBinding
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Inisialisasi ViewModel menggunakan Factory
        val dao = AppDatabase.getDatabase(this).studentDao()
        val factory = StudentViewModelFactory(dao)
        studentViewModel = ViewModelProvider(this, factory)[StudentViewModel::class.java]

        adapter = StudentAdapter(
            onDeleteClick = { student ->
                studentViewModel.deleteStudent(student)
                Toast.makeText(this, "Data Mahasiswa Dihapus", Toast.LENGTH_SHORT).show()
            }
        )

        // Pasang adapter ke RecyclerView
        binding.rvStudents.layoutManager = LinearLayoutManager(this)
        binding.rvStudents.adapter = adapter

        // Tombol Hapus Semua Data Mahasiswa
        binding.btnClearAll.setOnClickListener {
            studentViewModel.deleteAllStudents()
            Toast.makeText(this, "Semua Data Mahasiswa Dihapus", Toast.LENGTH_SHORT).show()
        }

        // 2. Mengamati aliran data StateFlow dari ViewModel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                studentViewModel.allStudents.collect { studentList ->
                    adapter.submitList(studentList)
                }
            }
        }

        // 3. Menyimpan data baru melalui ViewModel
        binding.btnSave.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val nim = binding.edtNim.text.toString().trim()
            val program = binding.edtProgram.text.toString().trim()
            if (name.isNotEmpty() && nim.isNotEmpty()) {
                studentViewModel.insertStudent(name, nim, program)
                Toast.makeText(this, "Data Mahasiswa Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                binding.edtName.text.clear()
                binding.edtNim.text.clear()
                binding.edtProgram.text.clear()
            } else {
                Toast.makeText(this, "Lengkapi semua data wajib", Toast.LENGTH_SHORT).show()
            }
        }
    }
}