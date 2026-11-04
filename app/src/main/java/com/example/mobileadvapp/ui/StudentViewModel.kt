package com.example.mobileadvapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileadvapp.data.local.Student
import com.example.mobileadvapp.data.local.StudentDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StudentViewModel(private val studentDao: StudentDao) : ViewModel() {

    val allStudents: StateFlow<List<Student>> = studentDao.getAllStudents()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insertStudent(name: String, nim: String, program: String) {
        viewModelScope.launch {
            studentDao.insertStudent(
                Student(
                    name = name,
                    nim = nim,
                    studyProgram = program
                )
            )
        }
    }

    fun deleteStudent(student: Student) {
        viewModelScope.launch {
            studentDao.deleteStudent(student)
        }
    }

    fun deleteAllStudents() {
        viewModelScope.launch {
            studentDao.deleteAllStudents()
        }
    }
}