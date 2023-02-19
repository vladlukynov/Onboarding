package com.src.onboarding.domain.model.course_page

data class CourseBlock(
    val id: Long,
    val name: String,
    val description: String,
    val countThemes: Int,
    val blocks: List<Block>

)