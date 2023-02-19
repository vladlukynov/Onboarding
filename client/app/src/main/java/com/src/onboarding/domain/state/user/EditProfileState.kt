package com.src.onboarding.domain.state.user

sealed class EditProfileState {
    object SuccessState : EditProfileState()
    object ErrorState : EditProfileState()
    object LoginAlreadyExistsState : EditProfileState()
    object DefaultState:EditProfileState()
    object LoadingState:EditProfileState()
}