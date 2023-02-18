package com.src.onboarding.presentation.courses.notifications.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.onboarding.domain.model.user.Notification
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.usecase.user.ClearNotificationsUseCase
import com.src.onboarding.domain.usecase.user.GetNotificationsUseCase
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val clearNotificationsUseCase: ClearNotificationsUseCase,
    private val getNotificationsUseCase: GetNotificationsUseCase
) : ViewModel() {
    private val _mutableLiveDataClearNotificationState =
        MutableLiveData<BasicState<Unit>>(BasicState.DefaultState())
    private val _mutableLiveDataGetNotificationsState =
        MutableLiveData<BasicState<List<Notification>>>(BasicState.LoadingState())
    val liveDataClearNotificationState get() = _mutableLiveDataClearNotificationState
    val liveDataGetNotificationsState get() = _mutableLiveDataGetNotificationsState

    fun getNotifications() {
        viewModelScope.launch {
            _mutableLiveDataGetNotificationsState.value = BasicState.LoadingState()
            _mutableLiveDataGetNotificationsState.value = getNotificationsUseCase.execute()
        }
    }

    fun clearNotifications() {
        viewModelScope.launch {
            _mutableLiveDataClearNotificationState.value = BasicState.LoadingState()
            _mutableLiveDataClearNotificationState.value = clearNotificationsUseCase.execute()
        }
    }
}