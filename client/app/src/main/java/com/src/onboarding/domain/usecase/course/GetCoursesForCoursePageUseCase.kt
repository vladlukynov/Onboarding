package com.src.onboarding.domain.usecase.course

import com.src.onboarding.domain.model.course_page.CourseBlock
import com.src.onboarding.domain.repository.CourseRepository
import com.src.onboarding.domain.state.login.BasicState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCoursesForCoursePageUseCase(private val courseRepository: CourseRepository) {
    suspend fun execute(courseId: Long): BasicState<CourseBlock> =
        withContext(Dispatchers.IO) {
            return@withContext courseRepository.getCourseForCoursePage(courseId)
        }
}