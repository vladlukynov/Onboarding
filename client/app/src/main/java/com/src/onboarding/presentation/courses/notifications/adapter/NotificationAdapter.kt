package com.src.onboarding.presentation.courses.notifications.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.src.onboarding.databinding.ViewHolderNotificationBinding
import com.src.onboarding.domain.user.Notification

class NotificationAdapter() :
    ListAdapter<Notification, NotificationAdapter.DataViewHolder>(NotificationDiffCallback()) {
    class DataViewHolder(private val binding: ViewHolderNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(notification: Notification) {
            binding.tvNotificationText.text = notification.content
            binding.tvDate.text = notification.date
        }

        private val RecyclerView.ViewHolder.context
            get() = this.itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ViewHolderNotificationBinding.inflate(
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


class NotificationDiffCallback : DiffUtil.ItemCallback<Notification>() {
    override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem == newItem
    }

}