package com.example.mobileadvapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mobileadvapp.data.local.AppDatabase
import com.example.mobileadvapp.data.local.Course
import com.example.mobileadvapp.data.local.CourseDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CourseDaoTest {

    private lateinit var courseDao: CourseDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        courseDao = db.courseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun getAllCoursesWhenEmpty() = runBlocking {
        val allCourses = courseDao.getAllCourses().first()
        assertTrue(allCourses.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetAllCourses() = runBlocking {
        val course1 = Course(courseCode = "CS101", courseName = "Pemrograman Mobile", credits = 3, lecturerName = "Dosen A")
        val course2 = Course(courseCode = "CS102", courseName = "Algoritma", credits = 4, lecturerName = "Dosen B")

        courseDao.insertCourse(course1)
        courseDao.insertCourse(course2)

        val allCourses = courseDao.getAllCourses().first()
        assertEquals(2, allCourses.size)
        // Diurutkan berdasarkan course_name ASC ("Algoritma" lalu "Pemrograman Mobile")
        assertEquals("Algoritma", allCourses[0].courseName)
        assertEquals("CS102", allCourses[0].courseCode)
        assertEquals(4, allCourses[0].credits)
        assertEquals("Dosen B", allCourses[0].lecturerName)

        assertEquals("Pemrograman Mobile", allCourses[1].courseName)
        assertEquals("CS101", allCourses[1].courseCode)
        assertEquals(3, allCourses[1].credits)
        assertEquals("Dosen A", allCourses[1].lecturerName)
    }

    @Test
    @Throws(Exception::class)
    fun getCoursesByCredits() = runBlocking {
        val course1 = Course(courseCode = "CS101", courseName = "Pemrograman Mobile", credits = 3, lecturerName = "Dosen A")
        val course2 = Course(courseCode = "CS102", courseName = "Basis Data", credits = 2, lecturerName = "Dosen B")

        courseDao.insertCourse(course1)
        courseDao.insertCourse(course2)

        val filteredCourses = courseDao.getCoursesByCredits(3).first()
        assertEquals(1, filteredCourses.size)
        assertEquals("Pemrograman Mobile", filteredCourses[0].courseName)
        assertEquals(3, filteredCourses[0].credits)
    }

    @Test
    @Throws(Exception::class)
    fun updateCourse() = runBlocking {
        val course = Course(courseCode = "CS101", courseName = "Pemrograman Mobile", credits = 3, lecturerName = "Dosen A")
        courseDao.insertCourse(course)

        val insertedCourse = courseDao.getAllCourses().first()[0]
        val updatedCourse = insertedCourse.copy(
            courseName = "Pemrograman Mobile Lanjut",
            credits = 4,
            lecturerName = "Dosen Utama",
        )
        courseDao.updateCourse(updatedCourse)

        val allCourses = courseDao.getAllCourses().first()
        assertEquals(1, allCourses.size)
        assertEquals("Pemrograman Mobile Lanjut", allCourses[0].courseName)
        assertEquals(4, allCourses[0].credits)
        assertEquals("Dosen Utama", allCourses[0].lecturerName)
    }

    @Test
    @Throws(Exception::class)
    fun deleteCourse() = runBlocking {
        val course1 = Course(courseCode = "CS101", courseName = "Pemrograman Mobile", credits = 3, lecturerName = "Dosen A")
        val course2 = Course(courseCode = "CS102", courseName = "Algoritma", credits = 4, lecturerName = "Dosen B")

        courseDao.insertCourse(course1)
        courseDao.insertCourse(course2)

        val initialCourses = courseDao.getAllCourses().first()
        assertEquals(2, initialCourses.size)

        val courseToDelete = initialCourses.first { course -> course.courseCode == "CS101" }
        courseDao.deleteCourse(courseToDelete)

        val remainingCourses = courseDao.getAllCourses().first()
        assertEquals(1, remainingCourses.size)
        assertEquals("Algoritma", remainingCourses[0].courseName)
    }
}
