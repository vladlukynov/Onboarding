package com.src.onboarding.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.R
import com.src.onboarding.app.App
import com.src.onboarding.presentation.hr.profile.viewModel.HrEmployeeProfileViewModel
import com.src.onboarding.presentation.hr.profile.viewModel.HrEmployeeProfileViewModelFactory
import com.src.onboarding.presentation.hr.team.HrTeamFragment
import com.src.onboarding.presentation.hr.team.viewModel.HrTeamViewModel
import com.src.onboarding.presentation.hr.team.viewModel.HrTeamViewModelFactory
import javax.inject.Inject

class HrActivity : AppCompatActivity() {
    @Inject
    lateinit var hrTeamViewModelFactory: HrTeamViewModelFactory

    @Inject
    lateinit var hrEmployeeProfileViewModelFactory: HrEmployeeProfileViewModelFactory

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
}
