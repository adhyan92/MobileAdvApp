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
import com.example.mobileadvapp.databinding.ActivityCourseBinding
import kotlinx.coroutines.launch

class CourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseBinding
    private lateinit var courseViewModel: CourseViewModel
    private lateinit var adapter: CourseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi ViewModel lewat Factory
        val dao = AppDatabase.getDatabase(this).courseDao()
        val factory = CourseViewModelFactory(dao)
        courseViewModel = ViewModelProvider(this, factory)[CourseViewModel::class.java]

        adapter = CourseAdapter(
            onDeleteClick = { course ->
                courseViewModel.deleteCourse(course)
                Toast.makeText(this, "Mata Kuliah Dihapus", Toast.LENGTH_SHORT).show()
            }
        )

        binding.rvCourses.layoutManager = LinearLayoutManager(this)
        binding.rvCourses.adapter = adapter

        // Tombol Hapus Semua Data Mata Kuliah
        binding.btnClearAll.setOnClickListener {
            courseViewModel.deleteAllCourses()
            Toast.makeText(this, "Semua Data Mata Kuliah Dihapus", Toast.LENGTH_SHORT).show()
        }

        // Amati StateFlow allCourses
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                courseViewModel.allCourses.collect { courses ->
                    adapter.submitList(courses)
                }
            }
        }

        // Simpan data baru
        binding.btnSave.setOnClickListener {
            val code = binding.edtCode.text.toString().trim()
            val name = binding.edtName.text.toString().trim()
            val credits = binding.edtCredits.text.toString().toIntOrNull()
            val lecturer = binding.edtLecturer.text.toString().trim()

            if (code.isNotEmpty() && name.isNotEmpty() && credits != null && lecturer.isNotEmpty()) {
                courseViewModel.insertCourse(code, name, credits, lecturer)
                Toast.makeText(this, "Data Mata Kuliah Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                binding.edtCode.text.clear()
                binding.edtName.text.clear()
                binding.edtCredits.text.clear()
                binding.edtLecturer.text.clear()
            } else {
                Toast.makeText(this, "Lengkapi semua data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}