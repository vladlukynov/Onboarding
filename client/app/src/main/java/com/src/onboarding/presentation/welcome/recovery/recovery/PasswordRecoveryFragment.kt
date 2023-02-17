package com.src.onboarding.presentation.welcome.recovery.recovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.src.onboarding.R
import com.src.onboarding.databinding.FragmentLoadingBinding
import com.src.onboarding.databinding.FragmentPasswordRecoveryBinding
import com.src.onboarding.domain.state.login.ChangePasswordState
import com.src.onboarding.presentation.LoginActivity
import com.src.onboarding.presentation.welcome.recovery.recovery.viewModel.PasswordRecoveryViewModel

class PasswordRecoveryFragment : Fragment() {
    private lateinit var binding: FragmentPasswordRecoveryBinding
    private lateinit var bindingLoading: FragmentLoadingBinding
    private lateinit var viewModel: PasswordRecoveryViewModel
    private var email: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordRecoveryBinding.inflate(inflater)
        bindingLoading = binding.loading
        val args = this.arguments
        val emailArgs = args?.getString(BUNDLE_EMAIL)
        if (emailArgs != null && emailArgs.isNotEmpty()) {
            email = emailArgs
        } else {
            (activity as LoginActivity).showSnackBar(null)
        }
        viewModel = (activity as LoginActivity).getPasswordRecoveryViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as LoginActivity).getPasswordRecoveryViewModel()
        viewModel.liveDataIsLoading.observe(
            this.viewLifecycleOwner, this::checkLoading
        )
        viewModel.liveDataChangePasswordState.observe(
            this.viewLifecycleOwner, this::checkState
        )
        binding.tvContinueButton.setOnClickListener {
            val password1 = binding.etPassword.text.toString().replace("\\s".toRegex(), "")
            val password2 = binding.etPasswordRepeat.text.toString().replace("\\s".toRegex(), "")
            if (password1 == password2 && password1.isNotEmpty()) {
                viewModel.changePassword(email = email, password = password1)
            } else {
                if (password1.isEmpty() || password2.isEmpty()) {
                    (activity as LoginActivity).showSnackBar(getString(R.string.fill_all_fields))
                } else if (password1 != password2) {
                    (activity as LoginActivity).showSnackBar(getString(R.string.password_mismatch))
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

    private fun checkState(state: ChangePasswordState) {
        when (state) {
            is ChangePasswordState.SuccessState -> {
            }
            is ChangePasswordState.WrongPasswordState -> {
                (activity as LoginActivity).showSnackBar(getString(R.string.invalid_code))

            }
            else -> {
                (activity as LoginActivity).showSnackBar(null)
            }
        }
    }

    companion object {
        const val BUNDLE_EMAIL = "email"
    }
}