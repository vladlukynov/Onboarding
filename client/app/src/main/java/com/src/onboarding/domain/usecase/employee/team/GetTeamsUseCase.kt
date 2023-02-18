package com.src.onboarding.domain.usecase.employee.team

import com.src.onboarding.domain.model.employee.team.Team
import com.src.onboarding.domain.repository.EmployeeRepository
import com.src.onboarding.domain.state.login.BasicState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetTeamsUseCase(private val employeeRepository: EmployeeRepository) {
    suspend fun execute(): BasicState<List<Team>> = withContext(Dispatchers.IO) {
        return@withContext employeeRepository.getTeams()
    }
}