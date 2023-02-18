package com.src.onboarding.data.remote.service

import com.src.onboarding.data.remote.model.user.notification.NotificationResponse
import com.src.onboarding.di.NetworkModule
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/notification/get-notifications")
    suspend fun getNotifications(): Response<List<NotificationResponse>>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/notification/get-count-notification")
    suspend fun getCountNotification(): Response<Long>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/notification/get-count-notification")
    suspend fun clearNotifications(): Response<Unit>
}