package com.src.onboarding.presentation.hr.add_task.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.usecase.task.AddTaskUseCase
import com.src.onboarding.domain.usecase.user.GetIdUseCase
import kotlinx.coroutines.launch

class AddTaskViewModel(
    private val addTaskUseCase: AddTaskUseCase,
    private val getIdUseCase: GetIdUseCase
) : ViewModel() {
    private val _mutableLiveDataAddTaskState =
        MutableLiveData<BasicState<Unit>>(BasicState.LoadingState())
    val liveDataAddTaskState get() = _mutableLiveDataAddTaskState

    fun addTask(userId: Long?, header: String, deadline: String) {
        viewModelScope.launch {
            liveDataAddTaskState.value = BasicState.LoadingState()
            var id = userId
            if (userId == null) {
                id = getIdUseCase.execute()
            }
            liveDataAddTaskState.value =
                addTaskUseCase.execute(userId = id!!, header = header, deadline = deadline)
        }
    }
}