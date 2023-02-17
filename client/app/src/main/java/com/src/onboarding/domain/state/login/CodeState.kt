package com.src.onboarding.domain.state.login

sealed class CodeState {
    object SuccessState : CodeState()
    object ErrorState : CodeState()
    object WrongCodeState : CodeState()
    object WrongEmailState : CodeState()
    object LoadingState : CodeState()
    object DefaultState : CodeState()
}