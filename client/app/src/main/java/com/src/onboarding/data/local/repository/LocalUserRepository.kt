package com.src.onboarding.data.local.repository

interface LocalUserRepository {
    suspend fun setIsActiveAndClearSession()
    suspend fun getPostId(): Long?
    suspend fun getId(): Long
}