package com.src.onboarding.presentation.welcome.recovery.recovery_email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.src.onboarding.databinding.FragmentLoadingBinding
import com.src.onboarding.databinding.FragmentPasswordRecoveryEnterEmailBinding
import com.src.onboarding.domain.state.login.CodeState
import com.src.onboarding.presentation.LoginActivity
import com.src.onboarding.presentation.utils.REGEX_EMAIL
import com.src.onboarding.presentation.welcome.recovery.recovery_code.PasswordRecoveryEnterCodeFragment
import com.src.onboarding.presentation.welcome.recovery.recovery_email.viewModel.PasswordRecoveryEnterEmailViewModel
import com.src.onboarding.presentation.welcome.registration.viewModel.RegistrationViewModel
import java.util.regex.Pattern

class PasswordRecoveryEnterEmailFragment : Fragment() {
    private lateinit var binding: FragmentPasswordRecoveryEnterEmailBinding
    private lateinit var viewModel: PasswordRecoveryEnterEmailViewModel
    private lateinit var bindingLoading: FragmentLoadingBinding
    private var onClickNext = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordRecoveryEnterEmailBinding.inflate(inflater)
        bindingLoading = binding.loading
        viewModel = (activity as LoginActivity).getPasswordRecoveryEnterEmailViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveDataIsLoading.observe(this.viewLifecycleOwner, this::checkLoading)
        viewModel.liveDataCodeState.observe(this.viewLifecycleOwner, this::checkCodeState)
        setOnClickListenerForNextButton()
    }

    private fun checkLoading(isLoading: Boolean) {
        if (isLoading) {
            bindingLoading.clLoadingPage.visibility = View.VISIBLE
        } else {
            bindingLoading.clLoadingPage.visibility = View.GONE
        }
    }

    private fun setOnClickListenerForNextButton() {
        binding.tvContinueButton.setOnClickListener {
            if (!onClickNext) {
                val email = binding.etEmail.text.toString()
                if (Pattern.matches(REGEX_EMAIL, email)) {
                    viewModel.sendCode(email)
                    onClickNext = true
                } else {
                    //TODO сообщить об некорректном имейле
                }
            }
        }
    }

    //TODO когда ошибка сообщить пользователю∆
    private fun checkCodeState(state: CodeState) {
        if (onClickNext) {
            when (state) {
                is CodeState.SuccessState -> {
                    val bundle = Bundle()
                    bundle.putString(
                        PasswordRecoveryEnterCodeFragment.BUNDLE_EMAIL,
                        binding.etEmail.text.toString()
                    )
                    val fragment = PasswordRecoveryEnterCodeFragment()
                    fragment.arguments = bundle
                    (activity as LoginActivity).replaceFragment(fragment)
                }
                is CodeState.WrongEmailState -> {
                    //TODo такой пароль не существует ошибка
                    onClickNext = false
                }
                else -> {
                    println("error")
                    onClickNext = false
                }
            }
        }
    }
}