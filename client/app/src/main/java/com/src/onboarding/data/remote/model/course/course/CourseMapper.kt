package com.src.onboarding.data.remote.model.course.course

import com.src.onboarding.data.remote.utils.Mapper
import com.src.onboarding.di.NetworkModule
import com.src.onboarding.domain.model.course.course.Course

class CourseMapper : Mapper<Course, CourseResponse> {
    override suspend fun mapFromResponseToModel(data: CourseResponse): Course {
        return Course(
            id = data.id,
            name = data.name,
            description = data.description,
            image = "${NetworkModule.BASE_URL}${NetworkModule.COURSE_SERVICE_BASE_URL}${data.image}",
            closed = data.closed,
            countThemes = data.countThemes,
            percentageOfCompletion = data.percentageOfCompletion
        )
    }
}