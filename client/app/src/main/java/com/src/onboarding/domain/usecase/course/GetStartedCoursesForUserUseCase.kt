package com.src.onboarding.domain.usecase.course

import com.src.onboarding.domain.model.course.course.Course
import com.src.onboarding.domain.repository.CourseRepository
import com.src.onboarding.domain.state.login.BasicState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetStartedCoursesForUserUseCase(private val courseRepository: CourseRepository) {
    suspend fun execute(): BasicState<List<Course>> = withContext(Dispatchers.IO) {
        return@withContext courseRepository.getStartedCoursesForUser()
    }
}