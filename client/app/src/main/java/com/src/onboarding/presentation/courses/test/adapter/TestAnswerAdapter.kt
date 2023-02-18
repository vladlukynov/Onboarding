package com.src.onboarding.presentation.courses.test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.src.onboarding.databinding.ViewHolderTestAnswerBinding
import com.src.onboarding.domain.model.test.Answer

class TestAnswerAdapter :
    ListAdapter<Answer, TestAnswerAdapter.DataViewHolder>(AnswerDiffCallBack()) {
    class DataViewHolder(private val binding: ViewHolderTestAnswerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(answer: Answer, answerLetter: String) {
            binding.tvAnswerVariant.text = answerLetter
            binding.tvAnswerText.text = answer.text
        }

        private val RecyclerView.ViewHolder.context
            get() = this.itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ViewHolderTestAnswerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DataViewHolder(binding)
    }

    private val answerLetterList = listOf("A", "B", "C", "D")

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item, answerLetterList[position])
    }
}

class AnswerDiffCallBack : DiffUtil.ItemCallback<Answer>() {
    override fun areItemsTheSame(oldItem: Answer, newItem: Answer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Answer, newItem: Answer): Boolean {
        return oldItem == newItem
    }

}