package com.src.onboarding.presentation.courses.details.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.domain.usecase.course.GetCoursesForCoursePageUseCase
import javax.inject.Inject

class CourseDetailsViewModelFactory @Inject constructor(private val getCoursesForCoursePageUseCase: GetCoursesForCoursePageUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CourseDetailsViewModel(getCoursesForCoursePageUseCase = getCoursesForCoursePageUseCase) as T
    }
}