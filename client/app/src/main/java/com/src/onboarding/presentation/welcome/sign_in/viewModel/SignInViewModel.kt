package com.src.onboarding.presentation.welcome.sign_in.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.onboarding.domain.state.login.LoginState
import com.src.onboarding.domain.usecase.login.GetPostIdUseCase
import com.src.onboarding.domain.usecase.login.SignInUseCase
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val getPostIdUseCase: GetPostIdUseCase
) : ViewModel() {
    private val _mutableLoginState = MutableLiveData<LoginState>(LoginState.SuccessState)
    private val _mutableLiveDataIsLoading = MutableLiveData(false)
    private val _mutableLiveDataPostId = MutableLiveData<Long?>(0)
    val liveDataIsLoading get() = _mutableLiveDataIsLoading
    val liveDataLoginState get() = _mutableLoginState
    val liveDataPostId get() = _mutableLiveDataPostId

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _mutableLiveDataIsLoading.value = true
            _mutableLoginState.value = signInUseCase.execute(email, password)
            _mutableLiveDataIsLoading.value = false
        }
    }

    fun getPostId() {
        viewModelScope.launch {
            _mutableLiveDataPostId.value = getPostIdUseCase.execute()
        }
    }
}