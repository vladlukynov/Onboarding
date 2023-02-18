package com.src.onboarding.presentation.hr.team.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.src.onboarding.databinding.ViewHolderTeammateBinding
import com.src.onboarding.domain.model.course.colleague.Colleague
import com.src.onboarding.presentation.courses.courses_main.adapter.ColleagueDiffCallback

class TeammateAdapter(private val onClickColleague: (item: Colleague) -> Unit) :
    ListAdapter<Colleague, TeammateAdapter.DataViewHolder>(ColleagueDiffCallback()) {
    class DataViewHolder(private val binding: ViewHolderTeammateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(colleague: Colleague, onClickColleague: (item: Colleague) -> Unit) {
            binding.tvColleagueName.text = colleague.name
            binding.tvPost.text = colleague.post
            Glide.with(context)
                .load(colleague.image)
                .into(binding.ivColleaguePicture)
            itemView.setOnClickListener {
                onClickColleague
            }
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
        holder.onBind(item, onClickColleague)
    }
}
