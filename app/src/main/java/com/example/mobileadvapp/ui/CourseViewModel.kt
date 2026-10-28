package com.example.mobileadvapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileadvapp.data.local.AppDatabase
import com.example.mobileadvapp.data.local.Course
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CourseViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = AppDatabase.getDatabase(app).courseDao()

    val courses: StateFlow<List<Course>> = dao.getAllCourses()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun save(course: Course) {
        viewModelScope.launch {
            if (course.id == 0) dao.insertCourse(course) else dao.updateCourse(course)
        }
    }

    fun delete(course: Course) {
        viewModelScope.launch { dao.deleteCourse(course) }
    }
}