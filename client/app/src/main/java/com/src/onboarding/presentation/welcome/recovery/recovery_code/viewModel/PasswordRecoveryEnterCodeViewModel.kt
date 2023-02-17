package com.src.onboarding.presentation.welcome.recovery.recovery_code.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.onboarding.domain.state.login.CodeState
import com.src.onboarding.domain.usecase.login.CheckRecoveryCodeUseCase
import com.src.onboarding.domain.usecase.login.SendCodeForRecoveryPasswordUseCase
import kotlinx.coroutines.launch

class PasswordRecoveryEnterCodeViewModel(
    private val checkRecoveryCodeUseCase: CheckRecoveryCodeUseCase,
    private val sendCodeForRecoveryPasswordUseCase: SendCodeForRecoveryPasswordUseCase
) : ViewModel() {
    private val _mutableLiveDataIsLoading = MutableLiveData(false)
    private val _mutableLiveDataCodeState = MutableLiveData<CodeState>(CodeState.DefaultState)
    private val _mutableLiveDataRepeatingCodeState =
        MutableLiveData<CodeState>(CodeState.SuccessState)
    val liveDataIsLoading get() = _mutableLiveDataIsLoading
    val liveDataCodeState get() = _mutableLiveDataCodeState
    val liveDataRepeatingCodeState get() = _mutableLiveDataRepeatingCodeState

    fun checkRecoveryCode(code: String, email: String) {
        viewModelScope.launch {
            _mutableLiveDataIsLoading.value = true
            _mutableLiveDataCodeState.value =
                checkRecoveryCodeUseCase.execute(code = code, email = email)
            _mutableLiveDataIsLoading.value = false
        }
    }

    fun sendRepeatingCodeState(email: String) {
        viewModelScope.launch {
            _mutableLiveDataRepeatingCodeState.value = CodeState.LoadingState
            _mutableLiveDataRepeatingCodeState.value =
                sendCodeForRecoveryPasswordUseCase.execute(email)
        }
    }

    fun setDefaultValueForCodeState() {
        _mutableLiveDataRepeatingCodeState.value = CodeState.DefaultState
    }
}