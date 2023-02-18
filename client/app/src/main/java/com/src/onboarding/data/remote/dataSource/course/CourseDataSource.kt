package com.src.onboarding.data.remote.dataSource.course

import com.src.onboarding.domain.model.course.colleague.Colleague
import com.src.onboarding.domain.model.course.course.Course
import com.src.onboarding.domain.model.course.course.MainCourse
import com.src.onboarding.domain.state.course.ColleagueState
import com.src.onboarding.domain.state.login.BasicState

interface CourseDataSource {
    suspend fun getColleagues(): ColleagueState<List<Colleague>>
    suspend fun getCourses(): BasicState<MainCourse>
    suspend fun getStartedCoursesForUser(): BasicState<List<Course>>
}