package com.src.onboarding.presentation.courses.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.src.onboarding.R
import com.src.onboarding.databinding.ViewHolderCourseBlockTitleBinding
import com.src.onboarding.databinding.ViewHolderCourseItemBinding
import com.src.onboarding.databinding.ViewHolderTaskTitleBinding
import com.src.onboarding.domain.model.course_page.BlockItemWithTitle
import com.src.onboarding.domain.model.course_page.utils.ConditionType

class CoursePageAdapter(private val onClickBlock: (item: BlockItemWithTitle.BlockItemModel) -> Unit) :
    ListAdapter<BlockItemWithTitle, CoursePageAdapter.DataViewHolder>(BlockItemWithTitleDiffCallback()) {
    private lateinit var binding: ViewBinding

    class DataViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            blockItemWithTitle: BlockItemWithTitle,
            onClickBlock: (item: BlockItemWithTitle.BlockItemModel) -> Unit
        ) {
            when (blockItemWithTitle) {
                is BlockItemWithTitle.BlockItemModel -> {
                    onBindBlockItem(blockItemWithTitle, onClickBlock)
                }
                is BlockItemWithTitle.TitleModel -> {
                    onBindTitle(blockItemWithTitle)
                }
            }
        }

        private fun onBindBlockItem(
            blockItemWithTitle: BlockItemWithTitle.BlockItemModel,
            onClickBlock: (item: BlockItemWithTitle.BlockItemModel) -> Unit
        ) {
            val bindingBlock = binding as ViewHolderCourseItemBinding
            bindingBlock.tvTitle.text = blockItemWithTitle.name
            when (blockItemWithTitle.condition) {
                ConditionType.PASSED -> {
                    bindingBlock.ivStatusIcon.setImageResource(R.drawable.ic_course_item_done)
                }
                ConditionType.READY -> {
                    bindingBlock.ivStatusIcon.setImageResource(R.drawable.ic_play_button)
                }
                else -> {
                    bindingBlock.ivStatusIcon.setImageResource(R.drawable.ic_course_item_closed)
                }
            }
            itemView.setOnClickListener {
                onClickBlock(blockItemWithTitle)
            }
        }

        private fun onBindTitle(title: BlockItemWithTitle.TitleModel) {
            val bindingTitle = binding as ViewHolderCourseBlockTitleBinding
            bindingTitle.tvTitle.text = title.title
        }

        private val RecyclerView.ViewHolder.context
            get() = this.itemView.context

    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is BlockItemWithTitle.TitleModel -> TYPE_TITLE
            is BlockItemWithTitle.BlockItemModel -> TYPE_BLOCK
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        when (viewType) {
            TYPE_BLOCK -> {
                binding = ViewHolderCourseItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
            TYPE_TITLE -> {
                binding = ViewHolderCourseBlockTitleBinding.inflate(
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
        holder.onBind(item, onClickBlock)
    }

    companion object {
        private const val TYPE_BLOCK = 0
        private const val TYPE_TITLE = 1
    }
}

class BlockItemWithTitleDiffCallback : DiffUtil.ItemCallback<BlockItemWithTitle>() {
    override fun areItemsTheSame(
        oldItem: BlockItemWithTitle,
        newItem: BlockItemWithTitle
    ): Boolean {
        if (oldItem is BlockItemWithTitle.TitleModel && newItem is BlockItemWithTitle.TitleModel) {
            return oldItem.title == newItem.title
        }
        if (oldItem is BlockItemWithTitle.BlockItemModel && newItem is BlockItemWithTitle.BlockItemModel) {
            return oldItem.id == newItem.id
        }
        return false
    }

    override fun areContentsTheSame(
        oldItem: BlockItemWithTitle,
        newItem: BlockItemWithTitle
    ): Boolean {
        return oldItem == newItem
    }
}
