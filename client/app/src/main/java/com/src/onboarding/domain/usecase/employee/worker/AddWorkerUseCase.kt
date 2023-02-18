package com.src.onboarding.domain.usecase.employee.worker

import com.src.onboarding.domain.repository.EmployeeRepository
import com.src.onboarding.domain.state.login.BasicState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddWorkerUseCase(private val employeeRepository: EmployeeRepository) {
    suspend fun execute(email: String, postId: Long, teamId: Long): BasicState<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext employeeRepository.addWorker(
                email = email.lowercase(),
                postId = postId,
                teamId = teamId
            )
        }
}