package com.src.onboarding.domain.model.task

sealed class TaskWithTitle {
    class TaskModel(
        val id: Long,
        val dateStart: String,
        val deadlineDate: String,
        val header: String,
        val description: String?,
        var completed: Boolean
    ) : TaskWithTitle()

    class TitleModel(
        val title: String
    ) : TaskWithTitle()

    companion object {
        fun convertTaskModelToTaskWithDateTaskModel(task: Task): TaskWithTitle =
            TaskModel(
                id = task.id,
                dateStart = task.dateStart,
                deadlineDate = task.deadlineDate,
                header = task.header,
                description = task.description,
                completed = task.completed
            )
    }
}