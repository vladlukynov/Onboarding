package com.src.onboarding.domain.state.login

sealed class ChangePasswordState {
    object SuccessState : ChangePasswordState()
    object ErrorState : ChangePasswordState()
    object WrongPasswordState : ChangePasswordState()
}