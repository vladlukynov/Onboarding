package com.src.onboarding.data.remote.dataSource.task

import com.src.onboarding.data.remote.model.task.TaskMapper
import com.src.onboarding.data.remote.service.TaskService
import com.src.onboarding.domain.model.task.Task
import com.src.onboarding.domain.state.login.BasicState

class TaskDataSourceImpl(private val taskService: TaskService, private val taskMapper: TaskMapper) :
    TaskDataSource {
    override suspend fun getTasksByUserId(userId: Long): BasicState<List<Task>> {
        val response = taskService.getTasksByUserId(userId = userId)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return BasicState.SuccessState(body.map { taskMapper.mapFromResponseToModel(it) })
            }
        }
        return BasicState.ErrorState()
    }

    override suspend fun setCompletedTask(taskId: Long, completed: Boolean): BasicState<Unit> {
        val response = taskService.setCompletedTask(taskId = taskId, completed = completed)
        if (response.isSuccessful) {
            return BasicState.SuccessState(Unit)
        }
        return BasicState.ErrorState()
    }

    override suspend fun addTask(
        userId: Long,
        header: String,
        description: String,
        deadline: String
    ): BasicState<Unit> {
        val response = taskService.addTask(
            userId = userId,
            header = header,
            description = description,
            deadline = deadline
        )
        if (response.isSuccessful) {
            return BasicState.SuccessState(Unit)
        }
        return BasicState.ErrorState()
    }

    override suspend fun getTasksByIsCompleted(completed: Boolean): BasicState<List<Task>> {
        val response = taskService.getTasksByIsCompleted(completed = completed)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return BasicState.SuccessState(body.map { taskMapper.mapFromResponseToModel(it) })
            }
        }
        return BasicState.ErrorState()
    }
}