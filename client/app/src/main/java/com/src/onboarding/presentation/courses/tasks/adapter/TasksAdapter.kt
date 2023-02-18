package com.src.onboarding.presentation.courses.tasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.src.onboarding.R
import com.src.onboarding.databinding.ViewHolderTaskBinding
import com.src.onboarding.databinding.ViewHolderTaskTitleBinding
import com.src.onboarding.domain.model.task.TaskWithTitle

class TasksAdapter(private val onClickTaskComplete: (item: TaskWithTitle.TaskModel) -> Unit) :
    ListAdapter<TaskWithTitle, TasksAdapter.DataViewHolder>(TaskWithTitleDiffCallback()) {
    private lateinit var binding: ViewBinding

    class DataViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            taskWithTitle: TaskWithTitle,
            onClickTaskComplete: (item: TaskWithTitle.TaskModel) -> Unit
        ) {
            when (taskWithTitle) {
                is TaskWithTitle.TaskModel -> {
                    onBindTask(taskWithTitle, onClickTaskComplete)
                }
                is TaskWithTitle.TitleModel -> {
                    onBindTitle(taskWithTitle)
                }
            }
        }

        private fun onBindTask(
            taskWithTitle: TaskWithTitle.TaskModel,
            onClickTaskComplete: (item: TaskWithTitle.TaskModel) -> Unit
        ) {
            val bindingTask = binding as ViewHolderTaskBinding
            bindingTask.tvTaskTitle.text = taskWithTitle.header
            bindingTask.tvCreateDate.text = taskWithTitle.dateStart
            bindingTask.tvDeadlineDate.text = taskWithTitle.deadlineDate
            if (taskWithTitle.completed) {
                bindingTask.ivDoneTaskButton.setImageResource(R.drawable.ic_course_item_done)
                bindingTask.ivDoneTaskButton.setColorFilter(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
                bindingTask.ivDoneTaskButton.setBackgroundResource(R.drawable.task_complete_background)
            } else {
                bindingTask.ivDoneTaskButton.setBackgroundResource(R.drawable.shape_task_done_item)
                bindingTask.ivDoneTaskButton.setImageResource(0)
            }
            bindingTask.ivDoneTaskButton.setOnClickListener {
                onClickTaskComplete(taskWithTitle)
            }
        }

        private fun onBindTitle(title: TaskWithTitle.TitleModel) {
            val bindingTitle = binding as ViewHolderTaskTitleBinding
            bindingTitle.tvTitle.text = title.title
        }

        private val RecyclerView.ViewHolder.context
            get() = this.itemView.context

    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TaskWithTitle.TitleModel -> TYPE_TITLE
            is TaskWithTitle.TaskModel -> TYPE_TASK
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        when (viewType) {
            TYPE_TASK -> {
                binding = ViewHolderTaskBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
            TYPE_TITLE -> {
                binding = ViewHolderTaskTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
        }
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item, onClickTaskComplete)
    }

    companion object {
        private const val TYPE_TASK = 0
        private const val TYPE_TITLE = 1
        private const val NO_TYPE = 2
    }

}

class TaskWithTitleDiffCallback : DiffUtil.ItemCallback<TaskWithTitle>() {
    override fun areItemsTheSame(oldItem: TaskWithTitle, newItem: TaskWithTitle): Boolean {
        if (oldItem is TaskWithTitle.TitleModel && newItem is TaskWithTitle.TitleModel) {
            return oldItem.title == newItem.title
        }
        if (oldItem is TaskWithTitle.TaskModel && newItem is TaskWithTitle.TaskModel) {
            return oldItem.id == newItem.id
        }
        return false
    }

    override fun areContentsTheSame(oldItem: TaskWithTitle, newItem: TaskWithTitle): Boolean {
        return oldItem == newItem
    }
}
