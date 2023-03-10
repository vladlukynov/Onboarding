package com.src.onboarding.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.src.onboarding.R
import com.src.onboarding.app.App
import com.src.onboarding.databinding.ActivityMainBinding
import com.src.onboarding.presentation.courses.courses_main.CoursesMainFragment
import com.src.onboarding.presentation.courses.courses_main.viewModel.CourseMainViewModel
import com.src.onboarding.presentation.courses.courses_main.viewModel.CourseMainViewModelFactory
import com.src.onboarding.presentation.courses.details.viewModel.CourseDetailsViewModel
import com.src.onboarding.presentation.courses.details.viewModel.CourseDetailsViewModelFactory
import com.src.onboarding.presentation.courses.notifications.viewModel.NotificationViewModel
import com.src.onboarding.presentation.courses.notifications.viewModel.NotificationViewModelFactory
import com.src.onboarding.presentation.courses.tasks.TasksFragment
import com.src.onboarding.presentation.courses.tasks.viewModel.TasksViewModel
import com.src.onboarding.presentation.courses.tasks.viewModel.TasksViewModelFactory
import com.src.onboarding.presentation.hr.add_employee.viewModel.AddEmployeeViewModel
import com.src.onboarding.presentation.hr.add_employee.viewModel.AddEmployeeViewModelFactory
import com.src.onboarding.presentation.hr.add_task.viewModel.AddTaskViewModel
import com.src.onboarding.presentation.hr.add_task.viewModel.AddTaskViewModelFactory
import com.src.onboarding.presentation.hr.profile.viewModel.HrEmployeeProfileViewModel
import com.src.onboarding.presentation.hr.profile.viewModel.HrEmployeeProfileViewModelFactory
import com.src.onboarding.presentation.hr.team.viewModel.HrTeamViewModel
import com.src.onboarding.presentation.hr.team.viewModel.HrTeamViewModelFactory
import com.src.onboarding.presentation.profile.user_profile.UserProfileFragment
import com.src.onboarding.presentation.profile.user_profile.viewModel.UserProfileViewModel
import com.src.onboarding.presentation.profile.user_profile.viewModel.UserProfileViewModelFactory
import com.src.onboarding.presentation.support.new_appeal.viewModel.NewAppealViewModel
import com.src.onboarding.presentation.support.new_appeal.viewModel.NewAppealViewModelFactory
import com.src.onboarding.presentation.support.support_page.ClientSupportFragment
import com.src.onboarding.presentation.support.support_page.viewModel.ClientSupportViewModel
import com.src.onboarding.presentation.support.support_page.viewModel.ClientSupportViewModelFactory

import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var hrTeamViewModelFactory: HrTeamViewModelFactory

    @Inject
    lateinit var hrEmployeeProfileViewModelFactory: HrEmployeeProfileViewModelFactory

    @Inject
    lateinit var addEmployeeViewModelFactory: AddEmployeeViewModelFactory

    @Inject
    lateinit var addTaskViewModelFactory: AddTaskViewModelFactory

    @Inject
    lateinit var clientSupportViewModelFactory: ClientSupportViewModelFactory

    @Inject
    lateinit var newAppealViewModelFactory: NewAppealViewModelFactory




    @Inject
    lateinit var courseMainViewModelFactory: CourseMainViewModelFactory

    @Inject
    lateinit var notificationViewModelFactory: NotificationViewModelFactory

    @Inject
    lateinit var tasksViewModelFactory: TasksViewModelFactory

    @Inject
    lateinit var courseDetailsViewModelFactory: CourseDetailsViewModelFactory

    @Inject
    lateinit var userProfileViewModelFactory: UserProfileViewModelFactory
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragmentWithoutBackStack(CoursesMainFragment())

        setOnBottomBarItemClickListener()
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

    private fun setOnBottomBarItemClickListener() {
        binding.bnvBottomBar.setOnItemSelectedListener {
            clearFragmentBackStack()
            when (it.itemId) {
                R.id.i_chats -> replaceFragmentWithoutBackStack(ClientSupportFragment())
                R.id.i_courses -> replaceFragmentWithoutBackStack(CoursesMainFragment())
                R.id.i_tasks -> replaceFragmentWithoutBackStack(TasksFragment())
                R.id.i_profile -> replaceFragmentWithoutBackStack(UserProfileFragment())
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun clearFragmentBackStack() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        // val fm = supportFragmentManager
        // for (i in 0 until fm.backStackEntryCount)
        //     fm.popBackStack()
    }

    private fun replaceFragmentWithoutBackStack(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .addToBackStack(null).commit()
    }

    fun getCourseViewModel(): CourseMainViewModel =
        ViewModelProvider(this, courseMainViewModelFactory)[CourseMainViewModel::class.java]

    fun getNotificationViewModel(): NotificationViewModel =
        ViewModelProvider(this, notificationViewModelFactory)[NotificationViewModel::class.java]

    fun getTasksViewModel(): TasksViewModel =
        ViewModelProvider(this, tasksViewModelFactory)[TasksViewModel::class.java]

    fun getUserProfileViewModel(): UserProfileViewModel =
        ViewModelProvider(this, userProfileViewModelFactory)[UserProfileViewModel::class.java]

    fun getCourseDetailsViewModel(): CourseDetailsViewModel =
        ViewModelProvider(this, courseDetailsViewModelFactory)[CourseDetailsViewModel::class.java]






    fun getHrTeamViewModel(): HrTeamViewModel =
        ViewModelProvider(this, hrTeamViewModelFactory)[HrTeamViewModel::class.java]

    fun getHrEmployeeProfileViewModel(): HrEmployeeProfileViewModel =
        ViewModelProvider(
            this,
            hrEmployeeProfileViewModelFactory
        )[HrEmployeeProfileViewModel::class.java]

    fun getAddEmployeeViewModel(): AddEmployeeViewModel =
        ViewModelProvider(this, addEmployeeViewModelFactory)[AddEmployeeViewModel::class.java]

    fun getAddViewModel(): AddTaskViewModel =
        ViewModelProvider(this, addTaskViewModelFactory)[AddTaskViewModel::class.java]

    fun getClientSupportViewModel(): ClientSupportViewModel =
        ViewModelProvider(this, clientSupportViewModelFactory)[ClientSupportViewModel::class.java]

    fun getNewAppealViewModel(): NewAppealViewModel =
        ViewModelProvider(this, newAppealViewModelFactory)[NewAppealViewModel::class.java]
}