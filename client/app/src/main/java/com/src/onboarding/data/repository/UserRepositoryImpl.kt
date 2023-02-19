package com.src.onboarding.data.repository

import com.src.onboarding.data.local.repository.LocalUserRepository
import com.src.onboarding.data.remote.dataSource.user.UserDataSource
import com.src.onboarding.domain.model.user.Activity
import com.src.onboarding.domain.model.user.UserProfile
import com.src.onboarding.domain.model.user.Notification
import com.src.onboarding.domain.repository.UserRepository
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.state.user.EditProfileState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class UserRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val userLocalUserRepository: LocalUserRepository
) : UserRepository {
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

    override suspend fun getUserById(id: Long): BasicState<UserProfile> =
        withContext(Dispatchers.IO) {
            return@withContext userDataSource.getUserById(id)

        }

    override suspend fun getActivities(): BasicState<List<Activity>> = withContext(Dispatchers.IO) {
        return@withContext userDataSource.getAllActivities()
    }

    override suspend fun logout(): BasicState<Unit> = withContext(Dispatchers.IO) {
        return@withContext userDataSource.logout()
    }

    override suspend fun editProfile(data: String, file: File?): EditProfileState =
        withContext(Dispatchers.IO) {
            return@withContext userDataSource.editProfile(data, file)
        }

    override suspend fun getId(): Long = withContext(Dispatchers.IO) {
        return@withContext userLocalUserRepository.getId()
    }
}