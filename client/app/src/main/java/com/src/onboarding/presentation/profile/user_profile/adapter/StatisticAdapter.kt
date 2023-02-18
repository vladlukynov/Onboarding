package com.src.onboarding.presentation.profile.user_profile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.src.onboarding.databinding.ViewHolderStatisticsBinding
import com.src.onboarding.domain.model.course.course.Course

//TODO добавить кликабельность
class StatisticAdapter(private val onClickCourse: (item: Course) -> Unit) :
    ListAdapter<Course, StatisticAdapter.DataViewHolder>(CourseDiffCallback()) {
    class DataViewHolder(private val binding: ViewHolderStatisticsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(course: Course, onClickCourse: (item: Course) -> Unit) {
            Glide.with(context)
                .load(course.image)
                .into(binding.ivCoursePicture)
            binding.tvCourseName.text = course.name
            binding.pbProgressBar.progress = course.percentageOfCompletion!!.toInt()
            binding.tvPercent.text = "${course.percentageOfCompletion.toInt()}%"
            itemView.setOnClickListener {
                onClickCourse(course)
            }
        }

        private val RecyclerView.ViewHolder.context
            get() = this.itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ViewHolderStatisticsBinding.inflate(
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