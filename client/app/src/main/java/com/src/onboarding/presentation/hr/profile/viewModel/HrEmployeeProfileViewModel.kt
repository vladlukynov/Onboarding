package com.src.onboarding.presentation.hr.profile.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.onboarding.domain.model.course.course.Course
import com.src.onboarding.domain.model.task.Task
import com.src.onboarding.domain.model.user.Activity
import com.src.onboarding.domain.model.user.UserProfile
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.usecase.course.GetStartedCoursesByIdForUserUseCase

import com.src.onboarding.domain.usecase.task.GetTasksByUserIdUseCase
import com.src.onboarding.domain.usecase.user.GetActivitiesUseCase
import com.src.onboarding.domain.usecase.user.GetUserByIdUseCase
import kotlinx.coroutines.launch
//TODO активити
class HrEmployeeProfileViewModel
    (
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getStartedCoursesByIdForUserUseCase: GetStartedCoursesByIdForUserUseCase,
    private val getActivitiesUseCase: GetActivitiesUseCase,
    private val getTasksByUserIdUseCase: GetTasksByUserIdUseCase
) : ViewModel() {
    private val _mutableLiveDataGetProfileState =
        MutableLiveData<BasicState<UserProfile>>(BasicState.LoadingState())
    private val _mutableLiveDataGetStartedCoursesForUserState =
        MutableLiveData<BasicState<List<Course>>>(BasicState.LoadingState())
    private val _mutableLiveDataGetActivitiesState =
        MutableLiveData<BasicState<List<Activity>>>(BasicState.LoadingState())
    private val _mutableLiveDataTasksState =
        MutableLiveData<BasicState<List<Task>>>(BasicState.LoadingState())
    val liveDataGetProfileState get() = _mutableLiveDataGetProfileState
    val liveDataGetStartedCourseState get() = _mutableLiveDataGetStartedCoursesForUserState
    val liveDataGetActivitiesState get() = _mutableLiveDataGetActivitiesState
    val liveDataTasksState get() = _mutableLiveDataTasksState

    fun getProfile(id: Long) {
        viewModelScope.launch {
            _mutableLiveDataGetProfileState.value = BasicState.LoadingState()
            _mutableLiveDataGetProfileState.value = getUserByIdUseCase.execute(id)
        }
    }

    fun getStartedCourses(id: Long) {
        viewModelScope.launch {
            _mutableLiveDataGetStartedCoursesForUserState.value = BasicState.LoadingState()
            _mutableLiveDataGetStartedCoursesForUserState.value =
                getStartedCoursesByIdForUserUseCase.execute(id = id)
        }
    }

    fun getActivities() {
        viewModelScope.launch {
            _mutableLiveDataGetActivitiesState.value = BasicState.LoadingState()
            _mutableLiveDataGetActivitiesState.value = getActivitiesUseCase.execute()
        }
    }

    fun getTasks(userId: Long) {
        viewModelScope.launch {
            _mutableLiveDataTasksState.value = BasicState.LoadingState()
            _mutableLiveDataTasksState.value = getTasksByUserIdUseCase.execute(userId = userId)
        }
    }
}