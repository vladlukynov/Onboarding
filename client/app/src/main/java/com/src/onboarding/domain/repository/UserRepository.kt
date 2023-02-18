package com.src.onboarding.domain.repository

import com.src.onboarding.domain.user.Notification
import com.src.onboarding.domain.state.login.BasicState

interface UserRepository {
    suspend fun getNotifications(): BasicState<List<Notification>>
    suspend fun getCountNotification(): BasicState<Long>
    suspend fun clearNotifications(): BasicState<Unit>
}