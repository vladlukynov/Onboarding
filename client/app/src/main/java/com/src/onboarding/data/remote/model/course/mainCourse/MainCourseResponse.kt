package com.src.onboarding.data.remote.model.course.mainCourse

import com.google.gson.annotations.SerializedName
import com.src.onboarding.data.remote.model.course.course.CourseResponse

@kotlinx.serialization.Serializable
data class MainCourseResponse(
    @SerializedName("allCourses")
    val allCourses: List<CourseResponse>,
    @SerializedName("currentCourse")
    val currentCourse: CourseResponse?
)