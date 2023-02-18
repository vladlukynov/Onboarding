package com.src.onboarding.data.remote.service

import com.src.onboarding.data.remote.model.user.activity.ActivityResponse
import com.src.onboarding.data.remote.model.user.notification.NotificationResponse
import com.src.onboarding.data.remote.model.user.user_profile.UserProfileResponse
import com.src.onboarding.di.NetworkModule
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UserService {
    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/notification/get-notifications")
    suspend fun getNotifications(): Response<List<NotificationResponse>>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/notification/get-count-notification")
    suspend fun getCountNotification(): Response<Long>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/notification/clear")
    suspend fun clearNotifications(): Response<Unit>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/user/profile")
    suspend fun getProfile(): Response<UserProfileResponse>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/activity/all")
    suspend fun getActivities(): Response<List<ActivityResponse>>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/user/logout")
    suspend fun logout(
        @Query("refreshToken", encoded = true) refreshToken: String
    ): Response<Unit>
}