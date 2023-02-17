package com.src.onboarding.data.remote.dataSource.course

import com.src.onboarding.domain.model.course.colleague.Colleague
import com.src.onboarding.domain.state.course.ColleagueState

interface CourseDataSource {
    suspend fun getColleagues(): ColleagueState<List<Colleague>>
}