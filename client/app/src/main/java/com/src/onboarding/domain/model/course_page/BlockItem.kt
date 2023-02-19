package com.src.onboarding.domain.model.course_page

import com.src.onboarding.domain.model.course_page.utils.BlockType
import com.src.onboarding.domain.model.course_page.utils.ConditionType

data class BlockItem(
    val id: Long,
    val name: String,
    val type: BlockType,
    val condition: ConditionType,
    val numberInBlock: Int
)