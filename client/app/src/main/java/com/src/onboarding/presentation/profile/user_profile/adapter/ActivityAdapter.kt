package com.src.onboarding.presentation.profile.user_profile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.src.onboarding.databinding.ViewHolderActivityBinding
import com.src.onboarding.databinding.ViewHolderStatisticsBinding
import com.src.onboarding.domain.model.user.Activity

class ActivityAdapter() :
    ListAdapter<Activity, ActivityAdapter.DataViewHolder>(ActivityDiffCallback()) {
    class DataViewHolder(private val binding: ViewHolderActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(activity: Activity) {
            binding.tvTaskText.text = activity.content
            var completion = activity.date
            if (activity.percent != null) {
                completion += ". " + activity.percent + "%"
            }
            binding.tvTaskCompletionDate.text = completion
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ViewHolderActivityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }


}

class ActivityDiffCallback : DiffUtil.ItemCallback<Activity>() {
    override fun areItemsTheSame(oldItem: Activity, newItem: Activity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Activity, newItem: Activity): Boolean {
        return oldItem == newItem
    }
}