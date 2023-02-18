package com.src.onboarding.domain.repository

import com.src.onboarding.domain.model.user.Activity
import com.src.onboarding.domain.model.user.UserProfile
import com.src.onboarding.domain.model.user.Notification
import com.src.onboarding.domain.state.login.BasicState

interface UserRepository {
    suspend fun getNotifications(): BasicState<List<Notification>>
    suspend fun getCountNotification(): BasicState<Long>
    suspend fun clearNotifications(): BasicState<Unit>
    suspend fun getProfile(): BasicState<UserProfile>
    suspend fun getActivities(): BasicState<List<Activity>>
    suspend fun logout(): BasicState<Unit>
}