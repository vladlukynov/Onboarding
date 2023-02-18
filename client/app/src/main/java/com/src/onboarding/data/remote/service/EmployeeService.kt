package com.src.onboarding.data.remote.service

import com.src.onboarding.data.remote.model.employee.post.PostResponse
import com.src.onboarding.data.remote.model.employee.team.TeamResponse
import com.src.onboarding.di.NetworkModule
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EmployeeService {
    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/post/all")
    suspend fun getPosts(): Response<List<PostResponse>>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/team/all")
    suspend fun getTeams(): Response<List<TeamResponse>>

    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/user/add-worker")
    suspend fun addWorker(
        @Query("email") email: String,
        @Query("newPost") postId: Long,
        @Query("newCommand") teamId: Long
    ): Response<Unit>
}