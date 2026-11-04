package com.example.mobileadvapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileadvapp.data.local.Course
import com.example.mobileadvapp.databinding.ItemCourseBinding

class CourseAdapter : ListAdapter<Course, CourseAdapter.CourseViewHolder>(DIFF) {

    class CourseViewHolder(val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = getItem(position)
        holder.binding.tvName.text = course.courseName
        holder.binding.tvCode.text = course.courseCode
        holder.binding.tvCredits.text = "SKS: ${course.credits}"
        holder.binding.tvLecturer.text = "Dosen: ${course.lecturerName}"
    }


    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Course>() {
            override fun areItemsTheSame(a: Course, b: Course) = a.id == b.id
            override fun areContentsTheSame(a: Course, b: Course) = a == b
        }
    }
}