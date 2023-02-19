package com.src.onboarding.domain.usecase.course

import com.src.onboarding.domain.repository.CourseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetStartedCoursesByIdForUserUseCase(private val courseRepository: CourseRepository) {
    suspend fun execute(id: Long) = withContext(Dispatchers.IO) {
        return@withContext courseRepository.getStartedCoursesByIdForUser(id = id)
    }
}