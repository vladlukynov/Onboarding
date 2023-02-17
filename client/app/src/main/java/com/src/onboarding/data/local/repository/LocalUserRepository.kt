package com.src.onboarding.data.local.repository

interface LocalUserRepository {
    suspend fun setIsActiveAndClearSession()
}