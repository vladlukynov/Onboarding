package com.src.onboarding.domain.repository

import com.src.onboarding.domain.model.course.colleague.Colleague
import com.src.onboarding.domain.model.course.course.Course
import com.src.onboarding.domain.model.course.course.MainCourse
import com.src.onboarding.domain.model.course_page.CourseBlock
import com.src.onboarding.domain.state.course.ColleagueState
import com.src.onboarding.domain.state.login.BasicState

interface CourseRepository {
    suspend fun getColleagues(): ColleagueState<List<Colleague>>
    suspend fun getCourses(): BasicState<MainCourse>
    suspend fun getStartedCoursesForUser(): BasicState<List<Course>>
    suspend fun getStartedCoursesByIdForUser(id: Long): BasicState<List<Course>>
    suspend fun getCourseForCoursePage(courseId: Long): BasicState<CourseBlock>
}