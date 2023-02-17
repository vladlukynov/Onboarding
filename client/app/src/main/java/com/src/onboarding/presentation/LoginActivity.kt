package com.src.onboarding.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.src.onboarding.R
import com.src.onboarding.app.App
import com.src.onboarding.presentation.welcome.recovery.recovery.viewModel.PasswordRecoveryViewModel
import com.src.onboarding.presentation.welcome.recovery.recovery.viewModel.PasswordRecoveryViewModelFactory
import com.src.onboarding.presentation.welcome.recovery.recovery_code.viewModel.PasswordRecoveryEnterCodeViewModel
import com.src.onboarding.presentation.welcome.recovery.recovery_code.viewModel.PasswordRecoveryEnterCodeViewModelFactory
import com.src.onboarding.presentation.welcome.recovery.recovery_email.viewModel.PasswordRecoveryEnterEmailViewModel
import com.src.onboarding.presentation.welcome.recovery.recovery_email.viewModel.PasswordRecoveryEnterEmailViewModelFactory
import com.src.onboarding.presentation.welcome.registration.viewModel.RegistrationViewModel
import com.src.onboarding.presentation.welcome.registration.viewModel.RegistrationViewModelFactory
import com.src.onboarding.presentation.welcome.sign_in.SignInFragment
import com.src.onboarding.presentation.welcome.sign_in.viewModel.SignInViewModel
import com.src.onboarding.presentation.welcome.sign_in.viewModel.SignInViewModelFactory
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var signInViewModelFactory: SignInViewModelFactory

    @Inject
    lateinit var registrationViewModelFactory: RegistrationViewModelFactory

    @Inject
    lateinit var passwordRecoveryEnterEmailViewModelFactory: PasswordRecoveryEnterEmailViewModelFactory

    @Inject
    lateinit var passwordRecoveryEnterCodeViewModelFactory: PasswordRecoveryEnterCodeViewModelFactory

    @Inject
    lateinit var passwordRecoveryViewModelFactory: PasswordRecoveryViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).appComponent.inject(this)
        setContentView(R.layout.activity_main)
        replaceFragment(SignInFragment())
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .addToBackStack(null).commit()
    }

    fun getSignInViewModel(): SignInViewModel =
        ViewModelProvider(this, signInViewModelFactory)[SignInViewModel::class.java]

    fun getRegistrationViewModel(): RegistrationViewModel =
        ViewModelProvider(this, registrationViewModelFactory)[RegistrationViewModel::class.java]

    fun getPasswordRecoveryEnterEmailViewModel(): PasswordRecoveryEnterEmailViewModel =
        ViewModelProvider(
            this, passwordRecoveryEnterEmailViewModelFactory
        )[PasswordRecoveryEnterEmailViewModel::class.java]

    fun getPasswordRecoveryEnterCodeViewModel(): PasswordRecoveryEnterCodeViewModel =
        ViewModelProvider(
            this, passwordRecoveryEnterCodeViewModelFactory
        )[PasswordRecoveryEnterCodeViewModel::class.java]

    fun getPasswordRecoveryViewModel(): PasswordRecoveryViewModel = ViewModelProvider(
        this, passwordRecoveryViewModelFactory
    )[PasswordRecoveryViewModel::class.java]
}