package com.src.onboarding.data.repository

import com.src.onboarding.data.remote.dataSource.employee.EmployeeDataSource
import com.src.onboarding.domain.model.employee.post.Post
import com.src.onboarding.domain.model.employee.team.Team
import com.src.onboarding.domain.repository.EmployeeRepository
import com.src.onboarding.domain.state.login.BasicState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmployeeRepositoryImpl(private val employeeDataSource: EmployeeDataSource) :
    EmployeeRepository {
    override suspend fun getPosts(): BasicState<List<Post>> = withContext(Dispatchers.IO) {
        return@withContext employeeDataSource.getPosts()
    }

    override suspend fun getTeams(): BasicState<List<Team>> = withContext(Dispatchers.IO) {
        return@withContext employeeDataSource.getTeams()
    }

    override suspend fun addWorker(email: String, postId: Long, teamId: Long): BasicState<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext employeeDataSource.addWorker(
                email = email,
                postId = postId,
                teamId = teamId
            )
        }
}