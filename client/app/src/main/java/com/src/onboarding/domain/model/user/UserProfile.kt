package com.src.onboarding.domain.model.user

data class UserProfile(
    val id: Long,
    val image: String?,
    val email: String,
    val post: String?,
    val team: String?,
    val description: String?,
    val name:String
)