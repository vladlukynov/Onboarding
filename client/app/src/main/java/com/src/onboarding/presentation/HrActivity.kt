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
import com.src.onboarding.presentation.support.support_page.ClientSupportFragment
import com.src.onboarding.presentation.support.new_appeal.viewModel.NewAppealViewModel
import com.src.onboarding.presentation.support.new_appeal.viewModel.NewAppealViewModelFactory
import com.src.onboarding.presentation.support.support_page.viewModel.ClientSupportViewModel
import com.src.onboarding.presentation.support.support_page.viewModel.ClientSupportViewModelFactory
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

    @Inject
    lateinit var clientSupportViewModelFactory: ClientSupportViewModelFactory

    @Inject
    lateinit var newAppealViewModelFactory: NewAppealViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)
        binding = ActivityHrBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragmentWithoutBackStack(HrTeamFragment())

        setOnBottomBarItemClickListener()
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .addToBackStack(null).commit()
    }

    private fun replaceFragmentWithoutBackStack(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun setOnBottomBarItemClickListener() {
        binding.bnvBottomBar.setOnItemSelectedListener {
            clearFragmentBackStack()
            when (it.itemId) {
                R.id.i_chats -> replaceFragmentWithoutBackStack(ClientSupportFragment())
                R.id.i_teams -> replaceFragmentWithoutBackStack(HrTeamFragment())
                R.id.i_profile -> replaceFragmentWithoutBackStack(HrUserProfileFragment())
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

    fun getClientSupportViewModel(): ClientSupportViewModel =
        ViewModelProvider(this, clientSupportViewModelFactory)[ClientSupportViewModel::class.java]

    fun getNewAppealViewModel(): NewAppealViewModel =
        ViewModelProvider(this, newAppealViewModelFactory)[NewAppealViewModel::class.java]
}
