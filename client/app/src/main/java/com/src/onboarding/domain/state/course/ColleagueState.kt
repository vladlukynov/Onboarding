package com.src.onboarding.domain.state.course


sealed class ColleagueState<out T : Any> {
    class SuccessState<out T : Any>(val data: T) : ColleagueState<T>()
    class ErrorState<out T : Any> : ColleagueState<T>()
    class NotWorkingState<out T : Any> : ColleagueState<T>()
    class LoadingState<out T : Any> : ColleagueState<T>()
}