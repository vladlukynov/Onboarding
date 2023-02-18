package com.src.onboarding.data.remote.dataSource.employee

import com.src.onboarding.domain.model.employee.post.Post
import com.src.onboarding.domain.model.employee.team.Team
import com.src.onboarding.domain.state.login.BasicState

interface EmployeeDataSource {
    suspend fun getPosts(): BasicState<List<Post>>
    suspend fun getTeams(): BasicState<List<Team>>
    suspend fun addWorker(email: String, postId: Long, teamId: Long): BasicState<Unit>
}