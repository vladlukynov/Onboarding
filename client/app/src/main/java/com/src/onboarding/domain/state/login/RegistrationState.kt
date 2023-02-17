package com.src.onboarding.domain.state.login

sealed class RegistrationState {
    object SuccessState : RegistrationState()
    object EmailAlreadyExistsState : RegistrationState()
    object ErrorState : RegistrationState()
    object DefaultState : RegistrationState()
}