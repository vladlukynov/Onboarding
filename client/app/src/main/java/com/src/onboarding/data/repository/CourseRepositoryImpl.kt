package com.src.onboarding.data.repository

import com.src.onboarding.data.remote.dataSource.course.CourseDataSource
import com.src.onboarding.domain.model.course.colleague.Colleague
import com.src.onboarding.domain.repository.CourseRepository
import com.src.onboarding.domain.state.course.ColleagueState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CourseRepositoryImpl(private val courseDataSource: CourseDataSource) : CourseRepository {
    override suspend fun getColleagues(): ColleagueState<List<Colleague>> =
        withContext(Dispatchers.IO) {
            return@withContext courseDataSource.getColleagues()
        }
}