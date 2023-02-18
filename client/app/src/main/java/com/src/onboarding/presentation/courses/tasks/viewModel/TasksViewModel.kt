package com.src.onboarding.presentation.courses.tasks.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.onboarding.domain.model.task.TaskWithTitle
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.usecase.task.GetTasksByIsCompletedUseCase
import com.src.onboarding.domain.usecase.task.SetCompletedTaskUseCase
import kotlinx.coroutines.launch

class TasksViewModel(
    private val getTasksByIsCompletedUseCase: GetTasksByIsCompletedUseCase,
    private val setCompletedTaskUseCase: SetCompletedTaskUseCase
) : ViewModel() {
    private val _mutableLiveDataGetTasksState =
        MutableLiveData<BasicState<ArrayList<TaskWithTitle>>>(BasicState.LoadingState())
    private val _mutableLiveDataSetCompletedTaskState =
        MutableLiveData<BasicState<Unit>>(BasicState.DefaultState())
    val liveDataGetTasksState get() = _mutableLiveDataGetTasksState
    val liveDataSetCompletedTaskState get() = _mutableLiveDataSetCompletedTaskState

    fun getTasks() {
        viewModelScope.launch {
            _mutableLiveDataGetTasksState.value = BasicState.LoadingState()
            val result = ArrayList<TaskWithTitle>(emptyList())
            val unfinishedTasks = getTasksByIsCompletedUseCase.execute(completed = false)
            val finishedTask = getTasksByIsCompletedUseCase.execute(completed = true)
            if (unfinishedTasks is BasicState.SuccessState) {
                Log.d("ViewModel","size=${unfinishedTasks.data.size}")
                if (unfinishedTasks.data.isNotEmpty()) {
                    result.add(TaskWithTitle.TitleModel("Незавершенные"))
                    result.addAll(unfinishedTasks.data.map {
                        TaskWithTitle.convertTaskModelToTaskWithTitleTaskModel(
                            it
                        )
                    })
                }
            }
            if (finishedTask is BasicState.SuccessState) {
                if (finishedTask.data.isNotEmpty()) {
                    Log.d("ViewModel finished","size=${finishedTask.data.size}")
                    result.add(TaskWithTitle.TitleModel("Завершенные"))
                    result.addAll(finishedTask.data.map {
                        TaskWithTitle.convertTaskModelToTaskWithTitleTaskModel(
                            it
                        )
                    })
                }
            }
            if (unfinishedTasks is BasicState.SuccessState && finishedTask is BasicState.SuccessState) {
                _mutableLiveDataGetTasksState.value = BasicState.SuccessState(result)
            } else {
                _mutableLiveDataGetTasksState.value = BasicState.ErrorState()
            }
        }
    }

    fun setCompletedForTask(taskId: Long, completed: Boolean) {
        viewModelScope.launch {
            _mutableLiveDataSetCompletedTaskState.value = BasicState.LoadingState()
            _mutableLiveDataSetCompletedTaskState.value =
                setCompletedTaskUseCase.execute(taskId = taskId, completed = completed)
        }
    }
}