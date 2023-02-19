package com.src.onboarding.data.remote.model.course.course_page.block_item

import com.src.onboarding.data.remote.utils.Mapper
import com.src.onboarding.domain.model.course_page.BlockItem
import com.src.onboarding.domain.model.course_page.utils.BlockType
import com.src.onboarding.domain.model.course_page.utils.ConditionType

class BlockItemMapper : Mapper<BlockItem, BlockItemResponse> {
    override suspend fun mapFromResponseToModel(data: BlockItemResponse): BlockItem {
        val conditionType = when (data.condition) {
            0 -> {
                ConditionType.PASSED
            }
            1 -> {
                ConditionType.READY
            }
            else -> {
                ConditionType.LOCKED
            }
        }
        val blockType = when (data.type) {
            0 -> {
                BlockType.FEEDBACK
            }
            1 -> {
                BlockType.TEST
            }
            else -> {
                BlockType.TEXT_MATERIAL
            }
        }
        return BlockItem(
            id = data.id,
            name = data.name,
            type = blockType,
            condition = conditionType,
            numberInBlock = data.numberInBlock
        )
    }
}