package com.src.onboarding.presentation.hr.team.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.onboarding.domain.model.course.colleague.Colleague
import com.src.onboarding.domain.model.user.UserProfile
import com.src.onboarding.domain.state.course.ColleagueState
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.usecase.course.GetColleaguesUseCase
import com.src.onboarding.domain.usecase.user.GetCountNotificationUseCase
import com.src.onboarding.domain.usecase.user.GetUserProfileUseCase
import kotlinx.coroutines.launch

class HrTeamViewModel(
    private val getColleaguesUseCase: GetColleaguesUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getCountNotificationsUseCase: GetCountNotificationUseCase
) : ViewModel() {
    private val _mutableLiveDataColleagueState =
        MutableLiveData<ColleagueState<List<Colleague>>>(ColleagueState.LoadingState())
    private val _mutableLiveDataGetProfileState =
        MutableLiveData<BasicState<UserProfile>>(BasicState.LoadingState())
    private val _mutableLiveDataGetCountNotificationsState =
        MutableLiveData<BasicState<Long>>(BasicState.LoadingState())
    val liveDataGetProfileState get() = _mutableLiveDataGetProfileState
    val liveDataColleagueState get() = _mutableLiveDataColleagueState
    val liveDataGetCountNotificationsState get() = _mutableLiveDataGetCountNotificationsState

    fun getColleagues() {
        viewModelScope.launch {
            _mutableLiveDataColleagueState.value = ColleagueState.LoadingState()
            _mutableLiveDataColleagueState.value = getColleaguesUseCase.execute()
        }
    }

    fun getProfile() {
        viewModelScope.launch {
            _mutableLiveDataGetProfileState.value = BasicState.LoadingState()
            _mutableLiveDataGetProfileState.value = getUserProfileUseCase.execute()
        }
    }

    fun getCountNotifications() {
        viewModelScope.launch {
            _mutableLiveDataGetCountNotificationsState.value = BasicState.LoadingState()
            _mutableLiveDataGetCountNotificationsState.value =
                getCountNotificationsUseCase.execute()
        }
    }

}