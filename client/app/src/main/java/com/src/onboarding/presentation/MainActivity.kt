package com.src.onboarding.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.R
import com.src.onboarding.app.App
import com.src.onboarding.presentation.courses.courses_main.CoursesMainFragment
import com.src.onboarding.presentation.courses.courses_main.viewModel.CourseMainViewModel
import com.src.onboarding.presentation.courses.courses_main.viewModel.CourseMainViewModelFactory

import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var courseMainViewModelFactory: CourseMainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)
        setContentView(R.layout.activity_main)
        replaceFragment(CoursesMainFragment())
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .addToBackStack(null).commit()
    }

    fun getCourseViewModel(): CourseMainViewModel =
        ViewModelProvider(this, courseMainViewModelFactory)[CourseMainViewModel::class.java]
}