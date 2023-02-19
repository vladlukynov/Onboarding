package com.src.onboarding.domain.model.course_page

import com.src.onboarding.domain.model.course_page.utils.BlockType
import com.src.onboarding.domain.model.course_page.utils.ConditionType

sealed class BlockItemWithTitle {
    class BlockItemModel(
        val id: Long,
        val name: String,
        val type: BlockType,
        val condition: ConditionType,
        val numberInBlock: Int
    ) : BlockItemWithTitle()

    class TitleModel(
        val title: String
    ) : BlockItemWithTitle()

    companion object {
        fun convertBlockModelToBlockWithTitleTaskModel(blockItem: BlockItem): BlockItemWithTitle =
            BlockItemModel(
                id = blockItem.id,
                name = blockItem.name,
                type = blockItem.type,
                condition = blockItem.condition,
                numberInBlock = blockItem.numberInBlock
            )
    }
}