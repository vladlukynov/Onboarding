package com.src.onboarding.data.remote.model.course.mainCourse

import com.src.onboarding.data.remote.model.course.course.CourseMapper
import com.src.onboarding.data.remote.utils.Mapper
import com.src.onboarding.domain.model.course.course.MainCourse

class MainCourseMapper(private val courseMapper: CourseMapper) :
    Mapper<MainCourse, MainCourseResponse> {
    override suspend fun mapFromResponseToModel(data: MainCourseResponse): MainCourse {
        return MainCourse(
            allCourses = data.allCourses.map { courseMapper.mapFromResponseToModel(it) },
            currentCourse = if (data.currentCourse != null) {
                courseMapper.mapFromResponseToModel(data.currentCourse)
            } else {
                null
            }
        )
    }
}