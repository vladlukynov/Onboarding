package com.src.onboarding.data.remote.service

import com.src.onboarding.data.remote.model.course.colleague.ColleagueResponse
import com.src.onboarding.di.NetworkModule
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface CourseService {
    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/user/colleagues")
    suspend fun getColleagues(
        @Header("Authorization") token: String?
    ): Response<List<ColleagueResponse>>
}