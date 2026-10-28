package com.example.mobileadvapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Insert
    suspend fun insertCourse(course: Course)

    @Query("SELECT * FROM courses ORDER BY course_name ASC")
    fun getAllCourses(): Flow<List<Course>>

    @Query("SELECT * FROM courses WHERE credits >= :minCredits")
    fun getCoursesByCredits(minCredits: Int): Flow<List<Course>>

    @Update
    suspend fun updateCourse(course: Course)

    @Delete
    suspend fun deleteCourse(course: Course)
}