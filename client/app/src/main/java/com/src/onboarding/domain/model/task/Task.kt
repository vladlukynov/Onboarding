package com.src.onboarding.domain.model.task

data class Task(
    val id: Long,
    val dateStart: String,
    val deadlineDate: String,
    val header: String,
    val description: String?,
    val completed: Boolean
)
