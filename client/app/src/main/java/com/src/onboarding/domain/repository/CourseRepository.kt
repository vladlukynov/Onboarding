package com.src.onboarding.domain.repository

import com.src.onboarding.domain.model.course.colleague.Colleague
import com.src.onboarding.domain.state.course.ColleagueState

interface CourseRepository {
    suspend fun getColleagues(): ColleagueState<List<Colleague>>
}