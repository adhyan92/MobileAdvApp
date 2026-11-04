package com.example.mobileadvapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileadvapp.data.local.Student
import com.example.mobileadvapp.databinding.ItemStudentBinding

class StudentAdapter : ListAdapter<Student, StudentAdapter.StudentViewHolder>(DIFF) {

    class StudentViewHolder(val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = getItem(position)
        holder.binding.tvName.text = student.name
        holder.binding.tvNim.text = "NIM: ${student.nim}"
        holder.binding.tvProgram.text = "Program Studi: ${student.studyProgram}"
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Student>() {
            override fun areItemsTheSame(a: Student, b: Student) = a.id == b.id
            override fun areContentsTheSame(a: Student, b: Student) = a == b
        }
    }
}