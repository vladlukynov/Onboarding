package com.src.onboarding.data.remote.dataSource.course

import com.src.onboarding.data.remote.model.course.colleague.ColleagueMapper
import com.src.onboarding.data.remote.service.CourseService
import com.src.onboarding.data.remote.session.SessionController
import com.src.onboarding.domain.model.course.colleague.Colleague
import com.src.onboarding.domain.state.course.ColleagueState

class CourseDataSourceImpl(
    private val courseService: CourseService,
    private val colleagueMapper: ColleagueMapper,
    private val sessionController: SessionController
) : CourseDataSource {
    override suspend fun getColleagues(): ColleagueState<List<Colleague>> {
        val response = courseService.getColleagues(
            token = sessionController.getToken()
        )
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

}