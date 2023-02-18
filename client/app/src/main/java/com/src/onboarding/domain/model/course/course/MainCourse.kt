package com.src.onboarding.domain.model.course.course

data class MainCourse(
    val allCourses: List<Course>,
    val currentCourse: Course?
)