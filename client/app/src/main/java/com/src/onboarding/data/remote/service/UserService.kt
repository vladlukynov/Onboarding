package com.src.onboarding.data.remote.service

import com.src.onboarding.data.remote.model.user.activity.ActivityResponse
import com.src.onboarding.data.remote.model.user.notification.NotificationResponse
import com.src.onboarding.data.remote.model.user.question.QuestionResponse
import com.src.onboarding.data.remote.model.user.user_profile.UserProfileResponse
import com.src.onboarding.di.NetworkModule
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/notification/get-notifications")
    suspend fun getNotifications(): Response<List<NotificationResponse>>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/notification/get-count-notification")
    suspend fun getCountNotification(): Response<Long>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/notification/clear")
    suspend fun clearNotifications(): Response<Unit>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/user/profile")
    suspend fun getProfile(): Response<UserProfileResponse>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/user/id/{userId}")
    suspend fun getUserById(@Path("userId") id: Long): Response<UserProfileResponse>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/activity/all")
    suspend fun getActivities(): Response<List<ActivityResponse>>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/user/logout")
    suspend fun logout(
        @Query("refreshToken", encoded = true) refreshToken: String
    ): Response<Unit>

    @Multipart
    @POST("${NetworkModule.USER_SERVICE_BASE_URL}/user/edit-profile")
    suspend fun editProfile(
        @Part("editUserBeanString") data: RequestBody,
        @Part file: MultipartBody.Part?
    ): Response<Unit>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/question/add-new-question")
    suspend fun addNewQuestion(
        @Query("text") text: String
    ): Response<Unit>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/question/add-answer")
    suspend fun addAnswer(
        @Query("idQuestion") questionId: Long,
        @Query("text") text: String
    ): Response<Unit>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/question/get-questions")
    suspend fun getQuestions(
        @Query("isCompleted") isCompleted: Boolean,
    ): Response<List<QuestionResponse>>

}