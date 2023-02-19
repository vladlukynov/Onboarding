package com.src.onboarding.presentation.support.support_page.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.src.onboarding.databinding.ViewHolderSupportAppealBinding
import com.src.onboarding.domain.model.user.Question

class HrAppealAdapter() :
    ListAdapter<Question, HrAppealAdapter.DataViewHolder>(HrAppealDiffCallback()) {

    class DataViewHolder(private val binding: ViewHolderSupportAppealBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(question: Question) {
            binding.tvAppealTitle.text = question.sender
            binding.tvAppealTime.text = question.dateSenderMessage
            binding.tvLastText.text = question.senderMessage
            Glide.with(context)
                .load(question.senderImg)
                .into(binding.ivAppealPicture)


        }

        private val RecyclerView.ViewHolder.context
            get() = this.itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ViewHolderSupportAppealBinding.inflate(
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

class HrAppealDiffCallback : DiffUtil.ItemCallback<Question>() {
    override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem == newItem
    }

}