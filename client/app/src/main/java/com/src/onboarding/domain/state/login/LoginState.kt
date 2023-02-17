package com.src.onboarding.domain.state.login

sealed class LoginState {
    object ErrorState : LoginState()
    object ErrorPasswordState : LoginState()
    object SuccessState : LoginState()
    object ErrorEmailState : LoginState()
}