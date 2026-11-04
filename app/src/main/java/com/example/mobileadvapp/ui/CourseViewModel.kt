package com.example.mobileadvapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileadvapp.data.local.Course
import com.example.mobileadvapp.data.local.CourseDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CourseViewModel(private val courseDao: CourseDao) : ViewModel() {

    val allCourses: StateFlow<List<Course>> = courseDao.getAllCourses()
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
}