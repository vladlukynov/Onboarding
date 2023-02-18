package com.src.onboarding.domain.model.course.course

data class Course(
    val id: Long,
    val name: String,
    val description: String?,
    val image: String?,
    val closed: Boolean,
    val countThemes: Int,
    val percentageOfCompletion: Double?
)