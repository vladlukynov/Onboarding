package com.src.onboarding.data.repository

import com.src.onboarding.data.remote.dataSource.task.TaskDataSource
import com.src.onboarding.domain.model.task.Task
import com.src.onboarding.domain.repository.TaskRepository
import com.src.onboarding.domain.state.login.BasicState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepositoryImpl(private val taskDataSource: TaskDataSource) : TaskRepository {
    override suspend fun getTasksByUserId(userId: Long): BasicState<List<Task>> =
        withContext(Dispatchers.IO) {
            return@withContext taskDataSource.getTasksByUserId(userId = userId)
        }

    override suspend fun setCompletedTask(taskId: Long, completed: Boolean): BasicState<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext taskDataSource.setCompletedTask(
                taskId = taskId,
                completed = completed
            )
        }

    override suspend fun addTask(
        userId: Long,
        header: String,
        description: String,
        deadline: String
    ): BasicState<Unit> = withContext(Dispatchers.IO) {
        return@withContext taskDataSource.addTask(
            userId = userId,
            header = header,
            description = description,
            deadline = deadline
        )
    }

    override suspend fun getTasksByIsCompleted(completed: Boolean): BasicState<List<Task>> =
        withContext(Dispatchers.IO) {
            return@withContext taskDataSource.getTasksByIsCompleted(completed = completed)
        }
}