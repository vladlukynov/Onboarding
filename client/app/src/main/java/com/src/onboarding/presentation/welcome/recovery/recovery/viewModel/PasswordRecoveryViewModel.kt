package com.src.onboarding.presentation.welcome.recovery.recovery.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.onboarding.domain.state.login.ChangePasswordState
import com.src.onboarding.domain.usecase.login.GetPostIdUseCase
import com.src.onboarding.domain.usecase.login.RecoverPasswordUseCase
import kotlinx.coroutines.launch

class PasswordRecoveryViewModel(
    private val changePasswordUseCase: RecoverPasswordUseCase,
    private val getPostIdUseCase: GetPostIdUseCase
) :
    ViewModel() {
    private val _mutableLiveDataIsLoading = MutableLiveData(false)
    private val _mutableLiveDataChangePasswordState =
        MutableLiveData<ChangePasswordState>(ChangePasswordState.DefaultState)
    private val _mutableLiveDataPostId = MutableLiveData<Long?>(-1)

    val liveDataIsLoading get() = _mutableLiveDataIsLoading
    val liveDataChangePasswordState get() = _mutableLiveDataChangePasswordState
    val liveDataPostId get() = _mutableLiveDataPostId

    fun changePassword(password: String) {
        viewModelScope.launch {
            _mutableLiveDataIsLoading.value = true
            _mutableLiveDataChangePasswordState.value =
                changePasswordUseCase.execute(password = password)
            _mutableLiveDataIsLoading.value = false
        }
    }

    fun getPostId() {
        viewModelScope.launch {
            _mutableLiveDataPostId.value = getPostIdUseCase.execute()
        }
    }
}