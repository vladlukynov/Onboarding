package com.src.onboarding.presentation.profile.user_profile.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.onboarding.domain.model.course.course.Course
import com.src.onboarding.domain.model.user.Activity
import com.src.onboarding.domain.model.user.UserProfile
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.usecase.course.GetStartedCoursesForUserUseCase
import com.src.onboarding.domain.usecase.user.GetActivitiesUseCase
import com.src.onboarding.domain.usecase.user.GetUserProfileUseCase
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getStartedCoursesForUserUseCase: GetStartedCoursesForUserUseCase,
    private val getActivitiesUseCase: GetActivitiesUseCase
) : ViewModel() {
    private val _mutableLiveDataGetProfileState =
        MutableLiveData<BasicState<UserProfile>>(BasicState.LoadingState())
    private val _mutableLiveDataGetStartedCoursesForUserState =
        MutableLiveData<BasicState<List<Course>>>(BasicState.LoadingState())
    private val _mutableLiveDataGetActivitiesState =
        MutableLiveData<BasicState<List<Activity>>>(BasicState.LoadingState())
    val liveDataGetProfileState get() = _mutableLiveDataGetProfileState
    val liveDataGetStartedCourseState get() = _mutableLiveDataGetStartedCoursesForUserState
    val liveDataGetActivitiesState get() = _mutableLiveDataGetActivitiesState
    fun getProfile() {
        viewModelScope.launch {
            _mutableLiveDataGetProfileState.value = BasicState.LoadingState()
            _mutableLiveDataGetProfileState.value = getUserProfileUseCase.execute()
        }
    }

    fun getStartedCourses() {
        viewModelScope.launch {
            _mutableLiveDataGetStartedCoursesForUserState.value = BasicState.LoadingState()
            _mutableLiveDataGetStartedCoursesForUserState.value =
                getStartedCoursesForUserUseCase.execute()
        }
    }

    fun getActivities() {
        viewModelScope.launch {
            _mutableLiveDataGetActivitiesState.value = BasicState.LoadingState()
            _mutableLiveDataGetActivitiesState.value = getActivitiesUseCase.execute()
        }
    }
}