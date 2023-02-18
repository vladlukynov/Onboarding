package com.src.onboarding.presentation.hr.add_employee.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.onboarding.domain.model.employee.post.Post
import com.src.onboarding.domain.model.employee.team.Team
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.usecase.employee.post.GetPostsUseCase
import com.src.onboarding.domain.usecase.employee.team.GetTeamsUseCase
import com.src.onboarding.domain.usecase.employee.worker.AddWorkerUseCase
import kotlinx.coroutines.launch

class AddEmployeeViewModel(
    private val addWorkerUseCase: AddWorkerUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val getTeamsUseCase: GetTeamsUseCase
) : ViewModel() {
    private val _mutableLiveDataAddWorkerState =
        MutableLiveData<BasicState<Unit>>(BasicState.LoadingState())
    private val _mutableLiveDataPostState =
        MutableLiveData<BasicState<List<Post>>>(BasicState.LoadingState())
    private val _mutableLiveDataTeamState =
        MutableLiveData<BasicState<List<Team>>>(BasicState.LoadingState())
    val liveDataPostState get() = _mutableLiveDataPostState
    val liveDataAddWorkerState get() = _mutableLiveDataAddWorkerState
    val liveDataTeamState get() = _mutableLiveDataTeamState
    fun getPosts() {
        viewModelScope.launch {
            _mutableLiveDataPostState.value = BasicState.LoadingState()
            _mutableLiveDataPostState.value = getPostsUseCase.execute()
        }
    }

    fun getTeams() {
        viewModelScope.launch {
            _mutableLiveDataTeamState.value = BasicState.LoadingState()
            _mutableLiveDataTeamState.value = getTeamsUseCase.execute()
        }
    }

    fun addWorker(email: String, postId: Long, teamId: Long) {
        viewModelScope.launch {
            _mutableLiveDataAddWorkerState.value = BasicState.LoadingState()
            _mutableLiveDataAddWorkerState.value =
                addWorkerUseCase.execute(email = email, postId = postId, teamId = teamId)
        }
    }
}