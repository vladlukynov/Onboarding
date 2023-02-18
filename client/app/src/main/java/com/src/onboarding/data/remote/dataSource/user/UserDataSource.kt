package com.src.onboarding.data.remote.dataSource.user

import com.src.onboarding.domain.model.user.Activity
import com.src.onboarding.domain.model.user.UserProfile
import com.src.onboarding.domain.model.user.Notification
import com.src.onboarding.domain.state.login.BasicState

interface UserDataSource {
    suspend fun getNotifications(): BasicState<List<Notification>>
    suspend fun getCountNotification(): BasicState<Long>
    suspend fun clearNotifications(): BasicState<Unit>
    suspend fun getProfile(): BasicState<UserProfile>
    suspend fun getAllActivities(): BasicState<List<Activity>>
}