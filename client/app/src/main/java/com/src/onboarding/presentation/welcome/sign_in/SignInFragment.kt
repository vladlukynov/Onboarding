package com.src.onboarding.presentation.welcome.sign_in

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.src.onboarding.R
import com.src.onboarding.databinding.FragmentLoadingBinding
import com.src.onboarding.databinding.FragmentSignInBinding
import com.src.onboarding.domain.state.login.LoginState
import com.src.onboarding.presentation.LoginActivity
import com.src.onboarding.presentation.MainActivity
import com.src.onboarding.presentation.utils.REGEX_EMAIL
import com.src.onboarding.presentation.welcome.recovery.recovery_email.PasswordRecoveryEnterEmailFragment
import com.src.onboarding.presentation.welcome.registration.RegistrationFragment
import com.src.onboarding.presentation.welcome.sign_in.viewModel.SignInViewModel
import java.util.regex.Pattern

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var bindingLoading: FragmentLoadingBinding
    private lateinit var viewModel: SignInViewModel
    private var isClickNext = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentSignInBinding.inflate(inflater)
        this.bindingLoading = binding.loginLoading
        viewModel = (activity as LoginActivity).getSignInViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveDataIsLoading.observe(
            this.viewLifecycleOwner, this::checkLoading
        )
        viewModel.liveDataLoginState.observe(
            this.viewLifecycleOwner, this::checkState
        )
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        setOnClickListenerForContinueButton()
        setOnClickListenerForRegisterButton()
        setOnCLickListenerForForgotPassword()
    }

    private fun setOnClickListenerForContinueButton() {
        binding.tvContinueButton.setOnClickListener {
            if (!isClickNext) {
                val emailWithoutSpace = removeAllSpaces(binding.etEmail.text.toString())
                val passwordWithoutSpace = removeAllSpaces(binding.etPassword.text.toString())
                if (!Pattern.matches(REGEX_EMAIL, emailWithoutSpace.toString())) {
                    (activity as LoginActivity).showSnackBar(getString(R.string.invalid_email))
                } else {
                    binding.etEmail.text
                    isClickNext = true
                    viewModel.signIn(
                        emailWithoutSpace!!,
                        passwordWithoutSpace!!,
                    )

                    startActivity(Intent(context, MainActivity::class.java).apply {
                    })
                }
            }
        }
    }

    private fun checkLoading(isLoading: Boolean) {
        if (isLoading) {
            bindingLoading.clLoadingPage.visibility = View.VISIBLE
        } else {
            bindingLoading.clLoadingPage.visibility = View.GONE
        }
    }

    private fun checkState(state: LoginState) {
        when (state) {
            is LoginState.SuccessState -> {
                if (isClickNext) {
                    //TODO перейти дальше
                }
            }
            is LoginState.ErrorEmailState -> {
                (activity as LoginActivity).showSnackBar(getString(R.string.email_error))
            }
            is LoginState.ErrorPasswordState -> {
                (activity as LoginActivity).showSnackBar(getString(R.string.password_error))
            }
            is LoginState.ErrorState -> {
                (activity as LoginActivity).showSnackBar(null)
            }
        }
        isClickNext = false
    }

    private fun removeAllSpaces(text: String?) = text?.replace("\\s".toRegex(), "")

    private fun setOnClickListenerForRegisterButton() {
        binding.tvSignIn.setOnClickListener {
            (activity as LoginActivity).replaceFragment(RegistrationFragment())
        }
    }

    private fun setOnCLickListenerForForgotPassword() {
        binding.tvForgotPassword.setOnClickListener {
            (activity as LoginActivity).replaceFragment(PasswordRecoveryEnterEmailFragment())
        }
    }
}