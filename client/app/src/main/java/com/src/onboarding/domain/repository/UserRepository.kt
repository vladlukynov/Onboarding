package com.src.onboarding.domain.repository

import com.src.onboarding.domain.model.user.Activity
import com.src.onboarding.domain.model.user.UserProfile
import com.src.onboarding.domain.model.user.Notification
import com.src.onboarding.domain.model.user.Question
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.state.user.EditProfileState
import java.io.File

interface UserRepository {
    suspend fun getNotifications(): BasicState<List<Notification>>
    suspend fun getCountNotification(): BasicState<Long>
    suspend fun clearNotifications(): BasicState<Unit>
    suspend fun getProfile(): BasicState<UserProfile>
    suspend fun getUserById(id: Long): BasicState<UserProfile>
    suspend fun getActivities(): BasicState<List<Activity>>
    suspend fun logout(): BasicState<Unit>
    suspend fun editProfile(data: String, file: File?): EditProfileState
    suspend fun getId(): Long
    suspend fun addNewQuestion(text: String): BasicState<Unit>

    suspend fun addAnswer(questionId: Long, text: String): BasicState<Unit>

    suspend fun getQuestions(isCompleted: Boolean): BasicState<List<Question>>
}