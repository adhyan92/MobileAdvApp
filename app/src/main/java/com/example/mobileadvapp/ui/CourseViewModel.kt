package com.example.mobileadvapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileadvapp.data.local.Course
import com.example.mobileadvapp.data.local.CourseDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import android.util.Log
import kotlinx.coroutines.flow.onEach

class CourseViewModel(private val courseDao: CourseDao) : ViewModel() {

    val allCourses: StateFlow<List<Course>> = courseDao.getAllCourses()
        .onEach { Log.d("CourseViewModel", "allCourses berubah: ${it.size} item") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insertCourse(code: String, name: String, credits: Int, lecturer: String) {
        viewModelScope.launch {
            courseDao.insertCourse(
                Course(
                    courseCode = code,
                    courseName = name,
                    credits = credits,
                    lecturerName = lecturer
                )
            )
        }
    }

    fun deleteCourse(course: Course) {
        viewModelScope.launch {
            courseDao.deleteCourse(course)
        }
    }

    fun deleteAllCourses() {
        viewModelScope.launch {
            courseDao.deleteAllCourses()
        }
    }
}