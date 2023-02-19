package com.src.onboarding.domain.usecase.task

import com.src.onboarding.domain.repository.TaskRepository
import com.src.onboarding.domain.state.login.BasicState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddTaskUseCase(private val taskRepository: TaskRepository) {
    suspend fun execute(
        userId: Long, header: String,
        deadline: String
    ): BasicState<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext taskRepository.addTask(
                userId = userId,
                header = header,
                deadline = deadline,
                description = ""
            )
        }
}