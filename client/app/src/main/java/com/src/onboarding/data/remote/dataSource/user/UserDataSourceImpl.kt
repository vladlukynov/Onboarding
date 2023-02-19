package com.src.onboarding.data.remote.dataSource.user

import com.src.onboarding.data.remote.model.user.activity.ActivityMapper
import com.src.onboarding.data.remote.model.user.notification.NotificationMapper
import com.src.onboarding.data.remote.model.user.user_profile.UserProfileMapper
import com.src.onboarding.data.remote.service.UserService
import com.src.onboarding.data.remote.session.SessionStorage
import com.src.onboarding.domain.model.user.Activity
import com.src.onboarding.domain.model.user.UserProfile
import com.src.onboarding.domain.model.user.Notification
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.state.user.EditProfileState
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserDataSourceImpl(
    private val userService: UserService,
    private val notificationMapper: NotificationMapper,
    private val userProfileMapper: UserProfileMapper,
    private val activityMapper: ActivityMapper,
    private val sessionStorage: SessionStorage
) : UserDataSource {
    override suspend fun getNotifications(): BasicState<List<Notification>> {
        val response = userService.getNotifications()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return BasicState.SuccessState(body.map {
                    notificationMapper.mapFromResponseToModel(
                        it
                    )
                })
            }
        }
        return BasicState.ErrorState()
    }

    override suspend fun getCountNotification(): BasicState<Long> {
        val response = userService.getCountNotification()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return BasicState.SuccessState(body)
            }
        }
        return BasicState.ErrorState()
    }

    override suspend fun clearNotifications(): BasicState<Unit> {
        val response = userService.clearNotifications()
        if (response.isSuccessful) {
            return BasicState.SuccessState(Unit)
        }
        return BasicState.ErrorState()
    }

    override suspend fun getProfile(): BasicState<UserProfile> {
        val response = userService.getProfile()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return BasicState.SuccessState(userProfileMapper.mapFromResponseToModel(body))
            }
        }
        return BasicState.ErrorState()
    }

    override suspend fun getAllActivities(): BasicState<List<Activity>> {
        val response = userService.getActivities()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return BasicState.SuccessState(body.map { activityMapper.mapFromResponseToModel(it) })
            }
        }
        return BasicState.ErrorState()
    }

    override suspend fun logout(): BasicState<Unit> {
        val refreshToken = sessionStorage.getRefreshToken()
        val response = userService.logout(refreshToken)
        return if (response.isSuccessful) {
            sessionStorage.clearSession()
            BasicState.SuccessState(Unit)
        } else {
            BasicState.ErrorState()
        }
    }

    override suspend fun editProfile(data: String, file: File?): EditProfileState {
        val dataMultipart = data.toRequestBody("text/plain".toMediaTypeOrNull())
        var part: MultipartBody.Part? = null
        if (file != null) {
            val fileMultipart = file.asRequestBody("image/*".toMediaTypeOrNull())
            part = MultipartBody.Part.createFormData("file", file.name, fileMultipart)
        }
        val response = userService.editProfile(dataMultipart, part)
        if (response.isSuccessful) {
            return EditProfileState.SuccessState
        } else {
            if (response.code() == 409) {
                return EditProfileState.LoginAlreadyExistsState
            }
        }
        return EditProfileState.ErrorState
    }
}