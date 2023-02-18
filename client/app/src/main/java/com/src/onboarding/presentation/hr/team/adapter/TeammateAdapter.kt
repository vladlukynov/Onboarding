package com.src.onboarding.presentation.hr.team.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.src.onboarding.databinding.ViewHolderTeammateBinding
import com.src.onboarding.domain.model.hr.teammate.Teammate

class TeammateAdapter() :
    ListAdapter<Teammate, TeammateAdapter.DataViewHolder>(TeammateDiffCallback()) {
    class DataViewHolder(private val binding: ViewHolderTeammateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(teammate: Teammate) {
            binding.tvColleagueName.text = teammate.colleagueName
            binding.tvPost.text = teammate.post
            Glide.with(context)
                .load(teammate.image)
                .into(binding.ivColleaguePicture)
        }

        private val RecyclerView.ViewHolder.context
            get() = this.itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ViewHolderTeammateBinding.inflate(
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

class TeammateDiffCallback : DiffUtil.ItemCallback<Teammate>() {
    override fun areItemsTheSame(oldItem: Teammate, newItem: Teammate): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Teammate, newItem: Teammate): Boolean {
        return oldItem == newItem
    }
}
