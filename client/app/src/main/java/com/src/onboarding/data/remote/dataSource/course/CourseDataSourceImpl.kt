package com.src.onboarding.data.remote.dataSource.course

import android.util.Log
import com.src.onboarding.data.remote.model.course.colleague.ColleagueMapper
import com.src.onboarding.data.remote.model.course.course.CourseMapper
import com.src.onboarding.data.remote.model.course.course_page.course.CourseBlockMapper
import com.src.onboarding.data.remote.model.course.course_page.course.CourseBlockResponse
import com.src.onboarding.data.remote.model.course.mainCourse.MainCourseMapper
import com.src.onboarding.data.remote.service.CourseService
import com.src.onboarding.data.remote.session.SessionController
import com.src.onboarding.domain.model.course.colleague.Colleague
import com.src.onboarding.domain.model.course.course.Course
import com.src.onboarding.domain.model.course.course.MainCourse
import com.src.onboarding.domain.model.course_page.CourseBlock
import com.src.onboarding.domain.state.course.ColleagueState
import com.src.onboarding.domain.state.login.BasicState
import retrofit2.Response

class CourseDataSourceImpl(
    private val courseService: CourseService,
    private val colleagueMapper: ColleagueMapper,
    private val mainCourseMapper: MainCourseMapper,
    private val sessionController: SessionController,
    private val courseMapper: CourseMapper,
    private val courseBlockMapper: CourseBlockMapper
) : CourseDataSource {
    override suspend fun getColleagues(): ColleagueState<List<Colleague>> {
        val response = courseService.getColleagues()
        Log.d("sessionController", sessionController.getToken()!!)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return ColleagueState.SuccessState(body.map {
                    colleagueMapper.mapFromResponseToModel(
                        it
                    )
                })
            }
        }
        if (response.code() == 409) {
            return ColleagueState.NotWorkingState()
        }
        return ColleagueState.ErrorState()
    }

    override suspend fun getCourses(): BasicState<MainCourse> {
        val response = courseService.getCourses(sessionController.getToken())
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return BasicState.SuccessState(mainCourseMapper.mapFromResponseToModel(body))
            }
        }
        return BasicState.ErrorState()
    }

    override suspend fun getStartedCoursesForUser(): BasicState<List<Course>> {
        val response = courseService.getStartedCoursesForUser(sessionController.getToken())
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return BasicState.SuccessState(body.map { courseMapper.mapFromResponseToModel(it) })
            }
        }
        return BasicState.ErrorState()
    }

    override suspend fun getStartedCoursesByIdForUser(id: Long): BasicState<List<Course>> {
        val response = courseService.getStartedCoursesByIdForUser(
            token = sessionController.getToken(),
            id = id
        )
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return BasicState.SuccessState(body.map { courseMapper.mapFromResponseToModel(it) })
            }
        }
        return BasicState.ErrorState()
    }

    override suspend fun getCourseForCoursePage(courseId: Long): BasicState<CourseBlock> {
        val response = courseService.getCourseForCoursePage(courseId)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return BasicState.SuccessState(courseBlockMapper.mapFromResponseToModel(body))
            }
        }
        return BasicState.ErrorState()
    }

}