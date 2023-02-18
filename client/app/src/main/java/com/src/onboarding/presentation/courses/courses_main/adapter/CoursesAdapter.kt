package com.src.onboarding.presentation.courses.courses_main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.src.onboarding.databinding.ViewHolderCourseCardBinding
import com.src.onboarding.domain.model.course.course.Course
import com.src.onboarding.presentation.utils.ThemeUtils

//TODo придумать что делать если картинка null
class CoursesAdapter(private val onClickCourse: (item: Course) -> Unit) :
    ListAdapter<Course, CoursesAdapter.DataViewHolder>(CourseDiffCallback()) {
    class DataViewHolder(private val binding: ViewHolderCourseCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(course: Course, onClickCourse: (item: Course) -> Unit) {
            Glide.with(context)
                .load(course.image)
                .into(binding.ivCoursePicture)
            binding.tvCourseName.text = course.name
            binding.tvThemeCount.text =
                ThemeUtils().convertCountOfTheme(course.countThemes)
            itemView.setOnClickListener {
                onClickCourse(course)
            }
        }

        private val RecyclerView.ViewHolder.context
            get() = this.itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ViewHolderCourseCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DataViewHolder(binding)
    }


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item, onClickCourse = onClickCourse)
    }
}

class CourseDiffCallback : DiffUtil.ItemCallback<Course>() {
    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem == newItem
    }

}