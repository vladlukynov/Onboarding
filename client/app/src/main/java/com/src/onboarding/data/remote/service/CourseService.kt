package com.src.onboarding.data.remote.service

import com.src.onboarding.data.remote.model.course.colleague.ColleagueResponse
import com.src.onboarding.data.remote.model.course.course.CourseResponse
import com.src.onboarding.data.remote.model.course.course_page.course.CourseBlockResponse
import com.src.onboarding.data.remote.model.course.mainCourse.MainCourseResponse
import com.src.onboarding.di.NetworkModule
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface CourseService {
    @GET("${NetworkModule.USER_SERVICE_BASE_URL}/user/colleagues")
    suspend fun getColleagues(): Response<List<ColleagueResponse>>

    @GET("${NetworkModule.COURSE_SERVICE_BASE_URL}/course/all")
    suspend fun getCourses(@Header("Authorization") token: String?): Response<MainCourseResponse>

    @GET("${NetworkModule.COURSE_SERVICE_BASE_URL}/course/started-courses")
    suspend fun getStartedCoursesForUser(@Header("Authorization") token: String?): Response<List<CourseResponse>>

    @GET("${NetworkModule.COURSE_SERVICE_BASE_URL}/course/started-courses-by-id")
    suspend fun getStartedCoursesByIdForUser(
        @Header("Authorization") token: String?,
        @Query("id") id: Long
    ): Response<List<CourseResponse>>

    @GET("${NetworkModule.COURSE_SERVICE_BASE_URL}/course/id/{courseId}")
    suspend fun getCourseForCoursePage(
        @Path("courseId") courseId: Long
    ): Response<CourseBlockResponse>
}