package com.src.onboarding.presentation.welcome.recovery.recovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        //TODO обработка ошибки если имейл не получен
        if (args?.getString(BUNDLE_EMAIL) != null) {
            email = args.getString(BUNDLE_EMAIL) as String
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
                //TODO обработать ошибку с несовпадением пароля/поля пустные
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

    //TODO обработать ошибки (подверждение кода)
    private fun checkState(state: ChangePasswordState) {
        when (state) {
            is ChangePasswordState.SuccessState -> {
                //TODO перейти на главный экран
                println("password has changed")
            }
            is ChangePasswordState.WrongPasswordState -> {

            }
            else -> {
                println("error")
            }
        }
    }

    companion object {
        const val BUNDLE_EMAIL = "email"
    }
}