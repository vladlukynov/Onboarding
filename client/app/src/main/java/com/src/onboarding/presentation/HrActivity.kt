package com.src.onboarding.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.R
import com.src.onboarding.app.App
import com.src.onboarding.databinding.ActivityHrBinding
import com.src.onboarding.presentation.hr.add_employee.viewModel.AddEmployeeViewModel
import com.src.onboarding.presentation.hr.add_employee.viewModel.AddEmployeeViewModelFactory
import com.src.onboarding.presentation.hr.add_task.viewModel.AddTaskViewModel
import com.src.onboarding.presentation.hr.add_task.viewModel.AddTaskViewModelFactory
import com.src.onboarding.presentation.hr.profile.viewModel.HrEmployeeProfileViewModel
import com.src.onboarding.presentation.hr.profile.viewModel.HrEmployeeProfileViewModelFactory
import com.src.onboarding.presentation.hr.team.HrTeamFragment
import com.src.onboarding.presentation.hr.team.viewModel.HrTeamViewModel
import com.src.onboarding.presentation.hr.team.viewModel.HrTeamViewModelFactory
import com.src.onboarding.presentation.hr.your_profile.profile.HrUserProfileFragment
import com.src.onboarding.presentation.hr.your_profile.profile.viewModel.HrUserProfileViewModel
import com.src.onboarding.presentation.hr.your_profile.profile.viewModel.HrUserProfileViewModelFactory
import com.src.onboarding.presentation.support.appeal_page.AppealPageFragment
import javax.inject.Inject

class HrActivity : AppCompatActivity() {
    @Inject
    lateinit var hrTeamViewModelFactory: HrTeamViewModelFactory

    @Inject
    lateinit var hrEmployeeProfileViewModelFactory: HrEmployeeProfileViewModelFactory

    @Inject
    lateinit var addEmployeeViewModelFactory: AddEmployeeViewModelFactory

    @Inject
    lateinit var userProfileViewModelFactory: HrUserProfileViewModelFactory
    private lateinit var binding: ActivityHrBinding

    @Inject
    lateinit var addTaskViewModelFactory: AddTaskViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)
        binding = ActivityHrBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HrTeamFragment())

        setOnBottomBarItemClickListener()
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .addToBackStack(null).commit()
    }

    private fun setOnBottomBarItemClickListener() {
        binding.bnvBottomBar.setOnItemSelectedListener {
            clearFragmentBackStack()
            when (it.itemId) {
                R.id.i_chats -> replaceFragment(AppealPageFragment())
                R.id.i_teams -> replaceFragment(HrTeamFragment())
                R.id.i_profile -> replaceFragment(HrUserProfileFragment())
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

    fun getHrTeamViewModel(): HrTeamViewModel =
        ViewModelProvider(this, hrTeamViewModelFactory)[HrTeamViewModel::class.java]

    fun getHrEmployeeProfileViewModel(): HrEmployeeProfileViewModel =
        ViewModelProvider(
            this,
            hrEmployeeProfileViewModelFactory
        )[HrEmployeeProfileViewModel::class.java]

    fun getAddEmployeeViewModel(): AddEmployeeViewModel =
        ViewModelProvider(this, addEmployeeViewModelFactory)[AddEmployeeViewModel::class.java]

    fun getUserProfileViewModel(): HrUserProfileViewModel =
        ViewModelProvider(this, userProfileViewModelFactory)[HrUserProfileViewModel::class.java]

    fun getAddViewModel(): AddTaskViewModel =
        ViewModelProvider(this, addTaskViewModelFactory)[AddTaskViewModel::class.java]
}
