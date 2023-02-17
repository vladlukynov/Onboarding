package com.src.onboarding.domain.usecase.course

import com.src.onboarding.domain.model.course.colleague.Colleague
import com.src.onboarding.domain.repository.CourseRepository
import com.src.onboarding.domain.state.course.ColleagueState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetColleaguesUseCase(private val courseRepository: CourseRepository) {
    suspend fun execute(): ColleagueState<List<Colleague>> = withContext(Dispatchers.IO) {
        return@withContext courseRepository.getColleagues()
    }
}