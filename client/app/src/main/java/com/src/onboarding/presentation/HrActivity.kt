package com.src.onboarding.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.R
import com.src.onboarding.app.App
import com.src.onboarding.presentation.hr.add_employee.viewModel.AddEmployeeViewModel
import com.src.onboarding.presentation.hr.add_employee.viewModel.AddEmployeeViewModelFactory
import com.src.onboarding.presentation.hr.profile.viewModel.HrEmployeeProfileViewModel
import com.src.onboarding.presentation.hr.profile.viewModel.HrEmployeeProfileViewModelFactory
import com.src.onboarding.presentation.hr.team.HrTeamFragment
import com.src.onboarding.presentation.hr.team.viewModel.HrTeamViewModel
import com.src.onboarding.presentation.hr.team.viewModel.HrTeamViewModelFactory
import com.src.onboarding.presentation.hr.your_profile.profile.viewModel.HrUserProfileViewModel
import com.src.onboarding.presentation.hr.your_profile.profile.viewModel.HrUserProfileViewModelFactory
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)
        setContentView(R.layout.activity_hr)
        replaceFragment(HrTeamFragment())
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .addToBackStack(null).commit()
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
}
