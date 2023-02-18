package com.src.onboarding.presentation.courses.courses_main.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.course.GetColleaguesUseCase
import com.src.onboarding.domain.usecase.course.GetCoursesUseCase
import javax.inject.Inject

class CourseMainViewModelFactory @Inject constructor(
    private val getColleaguesUseCase: GetColleaguesUseCase,
    private val getCoursesUseCase: GetCoursesUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CourseMainViewModel(
            getColleaguesUseCase = getColleaguesUseCase,
            getCoursesUseCase = getCoursesUseCase
        ) as T
    }
}