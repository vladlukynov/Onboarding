package com.src.onboarding.data.remote.dataSource.employee

import com.src.onboarding.data.remote.model.employee.post.PostMapper
import com.src.onboarding.data.remote.model.employee.team.TeamMapper
import com.src.onboarding.data.remote.service.EmployeeService
import com.src.onboarding.domain.model.employee.post.Post
import com.src.onboarding.domain.model.employee.team.Team
import com.src.onboarding.domain.state.login.BasicState

class EmployeeDataSourceImpl(
    private val employeeService: EmployeeService,
    private val postMapper: PostMapper,
    private val teamMapper: TeamMapper
) : EmployeeDataSource {
    override suspend fun getPosts(): BasicState<List<Post>> {
        val response = employeeService.getPosts()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return BasicState.SuccessState(body.map { postMapper.mapFromResponseToModel(it) })
            }
        }
        return BasicState.ErrorState()
    }

    override suspend fun getTeams(): BasicState<List<Team>> {
        val response = employeeService.getTeams()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return BasicState.SuccessState(body.map { teamMapper.mapFromResponseToModel(it) })
            }
        }
        return BasicState.ErrorState()
    }

    override suspend fun addWorker(email: String, postId: Long, teamId: Long): BasicState<Unit> {
        val response = employeeService.addWorker(email, postId, teamId)
        if (response.isSuccessful) {
            return BasicState.SuccessState(Unit)
        }
        return BasicState.ErrorState()
    }
}