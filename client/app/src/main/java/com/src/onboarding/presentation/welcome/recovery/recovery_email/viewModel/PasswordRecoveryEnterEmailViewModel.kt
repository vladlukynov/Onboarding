package com.src.onboarding.presentation.welcome.recovery.recovery_email.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.onboarding.domain.state.login.CodeState
import com.src.onboarding.domain.usecase.login.SendCodeForRecoveryPasswordUseCase
import kotlinx.coroutines.launch

class PasswordRecoveryEnterEmailViewModel(
    private val sendCodeForRecoveryPasswordUseCase: SendCodeForRecoveryPasswordUseCase
) :
    ViewModel() {
    private val _mutableLiveDataIsLoading = MutableLiveData(false)
    private val _mutableLiveDataCodeState = MutableLiveData<CodeState>(CodeState.SuccessState)

    val liveDataIsLoading get() = _mutableLiveDataIsLoading
    val liveDataCodeState get() = _mutableLiveDataCodeState

    fun sendCode(email: String) {
        viewModelScope.launch {
            _mutableLiveDataIsLoading.value = true
            _mutableLiveDataCodeState.value = sendCodeForRecoveryPasswordUseCase.execute(email)
            _mutableLiveDataIsLoading.value = false
        }
    }
}