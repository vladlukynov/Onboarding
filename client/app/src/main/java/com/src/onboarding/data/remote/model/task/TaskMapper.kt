package com.src.onboarding.data.remote.model.task

import com.src.onboarding.data.remote.utils.Mapper
import com.src.onboarding.domain.model.task.Task

class TaskMapper : Mapper<Task, TaskResponse> {
    override suspend fun mapFromResponseToModel(data: TaskResponse): Task {
        return Task(
            id = data.id,
            deadlineDate = data.deadlineDate,
            dateStart = data.dateStart,
            header = data.header,
            description = data.description,
            completed = data.completed
        )
    }
}