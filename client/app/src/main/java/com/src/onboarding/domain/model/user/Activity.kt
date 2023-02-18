package com.src.onboarding.domain.model.user

data class Activity(
    val id: Long,
    val content: String,
    val date: String,
    val percent: String?
)