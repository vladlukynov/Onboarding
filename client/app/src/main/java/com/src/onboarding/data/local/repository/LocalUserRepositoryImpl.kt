package com.src.onboarding.data.local.repository

import com.src.onboarding.data.remote.session.SessionStorage

class LocalUserRepositoryImpl(private val sessionStorage: SessionStorage) : LocalUserRepository {
    override suspend fun setIsActiveAndClearSession() {
        sessionStorage.clearSession()
        sessionStorage.setIsActive(true)
    }

    override suspend fun getPostId():Long? {
        return sessionStorage.getPostId()
    }

    override suspend fun getId(): Long {
        return sessionStorage.getId().toLong()
    }
}