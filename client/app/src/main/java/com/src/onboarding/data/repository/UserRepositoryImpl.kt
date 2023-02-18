package com.src.onboarding.data.repository

import com.src.onboarding.data.remote.dataSource.user.UserDataSource
import com.src.onboarding.domain.model.user.UserProfile
import com.src.onboarding.domain.user.Notification
import com.src.onboarding.domain.repository.UserRepository
import com.src.onboarding.domain.state.login.BasicState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(private val userDataSource: UserDataSource) : UserRepository {
    override suspend fun getNotifications(): BasicState<List<Notification>> =
        withContext(Dispatchers.IO) {
            return@withContext userDataSource.getNotifications()
        }

    override suspend fun getCountNotification(): BasicState<Long> = withContext(Dispatchers.IO) {
        return@withContext userDataSource.getCountNotification()
    }

    override suspend fun clearNotifications(): BasicState<Unit> = withContext(Dispatchers.IO) {
        return@withContext userDataSource.clearNotifications()
    }

    override suspend fun getProfile(): BasicState<UserProfile> = withContext(Dispatchers.IO) {
        return@withContext userDataSource.getProfile()
    }
}