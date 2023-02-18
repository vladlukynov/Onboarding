package com.src.onboarding.data.remote.service

import com.src.onboarding.data.remote.model.task.TaskResponse
import com.src.onboarding.di.NetworkModule
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TaskService {
    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/task/get-tasks-by-user-id")
    suspend fun getTasksByUserId(@Query("userId") userId: Long): Response<List<TaskResponse>>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/task/set-competed-task")
    suspend fun setCompletedTask(
        @Query("taskId") taskId: Long,
        @Query("completed") completed: Boolean
    ): Response<Unit>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/task/add-new-task")
    suspend fun addTask(
        @Query("userId") userId: Long,
        @Query("header") header: String,
        @Query("description") description: String,
        @Query("deadline") deadline: String
    ): Response<Unit>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/task/get-task-filter")
    suspend fun getTasksByIsCompleted(@Query("completed") completed: Boolean): Response<List<TaskResponse>>
}