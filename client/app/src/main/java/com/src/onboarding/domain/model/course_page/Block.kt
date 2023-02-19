package com.src.onboarding.domain.model.course_page

data class Block(
    val id: Long,
    val name: String,
    val itemInBlock: List<BlockItem>
)