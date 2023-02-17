package com.src.onboarding.presentation.courses.courses_main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.src.onboarding.databinding.ViewHolderColleagueBinding
import com.src.onboarding.domain.model.course.colleague.Colleague

class ColleagueAdapter(private val onClickColleague: (item: Colleague) -> Unit) :
    ListAdapter<Colleague, ColleagueAdapter.DataViewHolder>(ColleagueDiffCallback()) {

    class DataViewHolder(private val binding: ViewHolderColleagueBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(colleague: Colleague, onClickColleague: (item: Colleague) -> Unit) {
            Glide.with(context)
                .load(colleague.image)
                .into(binding.ivColleaguePicture)
            binding.tvColleagueName.text = colleague.name
            if (colleague.post != null) {
                binding.tvPost.text = colleague.post
            } else {
                binding.tvPost.visibility = View.GONE
            }
            itemView.setOnClickListener {
                onClickColleague(colleague)
            }
        }

        private val RecyclerView.ViewHolder.context
            get() = this.itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ViewHolderColleagueBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item, onClickColleague = onClickColleague)
    }
}


class ColleagueDiffCallback : DiffUtil.ItemCallback<Colleague>() {
    override fun areItemsTheSame(oldItem: Colleague, newItem: Colleague): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Colleague, newItem: Colleague): Boolean {
        return oldItem == newItem
    }


}