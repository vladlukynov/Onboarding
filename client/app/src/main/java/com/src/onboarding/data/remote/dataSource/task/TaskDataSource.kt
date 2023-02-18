package com.src.onboarding.data.remote.dataSource.task

import com.src.onboarding.domain.model.task.Task
import com.src.onboarding.domain.state.login.BasicState

interface TaskDataSource {
    suspend fun getTasksByUserId(userId: Long): BasicState<List<Task>>
    suspend fun setCompletedTask(taskId: Long, completed: Boolean): BasicState<Unit>
    suspend fun addTask(
        userId: Long,
        header: String,
        description: String,
        deadline: String
    ): BasicState<Unit>

    suspend fun getTasksByIsCompleted(completed: Boolean): BasicState<List<Task>>
}