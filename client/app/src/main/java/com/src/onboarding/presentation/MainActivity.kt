package com.src.onboarding.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.src.onboarding.R
import com.src.onboarding.app.App
import com.src.onboarding.presentation.hr.add_employee.viewModel.AddEmployeeViewModel
import com.src.onboarding.presentation.hr.add_employee.viewModel.AddEmployeeViewModelFactory
import com.src.onboarding.presentation.courses.courses_main.CoursesMainFragment
import com.src.onboarding.presentation.courses.courses_main.viewModel.CourseMainViewModel
import com.src.onboarding.presentation.courses.courses_main.viewModel.CourseMainViewModelFactory
import com.src.onboarding.presentation.courses.notifications.NotificationsFragment
import com.src.onboarding.presentation.courses.notifications.viewModel.NotificationViewModel
import com.src.onboarding.presentation.courses.notifications.viewModel.NotificationViewModelFactory
import com.src.onboarding.presentation.courses.tasks.TasksFragment
import com.src.onboarding.presentation.courses.tasks.viewModel.TasksViewModel
import com.src.onboarding.presentation.courses.tasks.viewModel.TasksViewModelFactory

import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var courseMainViewModelFactory: CourseMainViewModelFactory

    @Inject
    lateinit var addEmployeeViewModelFactory: AddEmployeeViewModelFactory

    @Inject
    lateinit var notificationViewModelFactory: NotificationViewModelFactory

    @Inject
    lateinit var tasksViewModelFactory: TasksViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)
        setContentView(R.layout.activity_main)
        replaceFragment(CoursesMainFragment())
    }

    @SuppressLint("ShowToast", "RestrictedApi")
    fun showSnackBar(text: String?) {
        val customView = layoutInflater.inflate(
            R.layout.snackbar_error,
            findViewById(R.id.cl_toast)
        )
        if (text != null) {
            val textView = customView.findViewById<TextView>(R.id.tv_text_error)
            textView.text = text
        }
        val snackBar = Snackbar.make(
            findViewById(R.id.fragment_container),
            "",
            Snackbar.LENGTH_LONG
        )
        snackBar.view.setBackgroundColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.transparent
            )
        )
        snackBar.show()
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .addToBackStack(null).commit()
    }

    fun getCourseViewModel(): CourseMainViewModel =
        ViewModelProvider(this, courseMainViewModelFactory)[CourseMainViewModel::class.java]

    fun getAddEmployeeViewModel(): AddEmployeeViewModel =
        ViewModelProvider(this, addEmployeeViewModelFactory)[AddEmployeeViewModel::class.java]

    fun getNotificationViewModel(): NotificationViewModel =
        ViewModelProvider(this, notificationViewModelFactory)[NotificationViewModel::class.java]

    fun getTasksViewModel(): TasksViewModel =
        ViewModelProvider(this, tasksViewModelFactory)[TasksViewModel::class.java]
}