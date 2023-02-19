package com.src.onboarding.data.repository

import com.src.onboarding.data.remote.dataSource.course.CourseDataSource
import com.src.onboarding.domain.model.course.colleague.Colleague
import com.src.onboarding.domain.model.course.course.Course
import com.src.onboarding.domain.model.course.course.MainCourse
import com.src.onboarding.domain.repository.CourseRepository
import com.src.onboarding.domain.state.course.ColleagueState
import com.src.onboarding.domain.state.login.BasicState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CourseRepositoryImpl(private val courseDataSource: CourseDataSource) : CourseRepository {
    override suspend fun getColleagues(): ColleagueState<List<Colleague>> =
        withContext(Dispatchers.IO) {
            return@withContext courseDataSource.getColleagues()
        }

    override suspend fun getCourses(): BasicState<MainCourse> = withContext(Dispatchers.IO) {
        return@withContext courseDataSource.getCourses()
    }

    override suspend fun getStartedCoursesForUser(): BasicState<List<Course>> =
        withContext(Dispatchers.IO) {
            return@withContext courseDataSource.getStartedCoursesForUser()
        }

    override suspend fun getStartedCoursesByIdForUser(id: Long): BasicState<List<Course>> =
        withContext(Dispatchers.IO) {
            return@withContext courseDataSource.getStartedCoursesByIdForUser(id = id)
        }
}