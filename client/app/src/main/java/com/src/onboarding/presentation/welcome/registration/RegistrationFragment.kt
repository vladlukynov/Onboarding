package com.src.onboarding.presentation.welcome.registration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.src.onboarding.databinding.FragmentLoadingBinding
import com.src.onboarding.databinding.FragmentRegistrationBinding
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.presentation.LoginActivity
import com.src.onboarding.presentation.MainActivity
import com.src.onboarding.presentation.utils.REGEX_EMAIL
import com.src.onboarding.presentation.welcome.name_registration.NameRegistrationFragment
import com.src.onboarding.presentation.welcome.registration.viewModel.RegistrationViewModel
import com.src.onboarding.presentation.welcome.sign_in.SignInFragment
import java.util.regex.Pattern

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var viewModel: RegistrationViewModel
    private lateinit var bindingLoading: FragmentLoadingBinding
    private var onClickNext = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater)
        bindingLoading = binding.loginLoading
        viewModel = (activity as LoginActivity).getRegistrationViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveDataIsLoading.observe(this.viewLifecycleOwner, this::checkLoading)
        viewModel.liveDataEmailExists.observe(this.viewLifecycleOwner, this::checkEmailExists)
        if (viewModel.liveDataEmail.value != null) {
            binding.etEmail.setText(viewModel.liveDataEmail.value)
        }
        binding.tvContinueButton.setOnClickListener {
            if (!onClickNext) {
                val emailWithoutSpace = removeAllSpaces(binding.etEmail.text.toString())

                if (emailWithoutSpace == null ||
                    !Pattern.matches(REGEX_EMAIL, emailWithoutSpace.toString())
                ) {
                    //TODO сообщить  некорректная почта
                } else {
                    viewModel.setEmail(emailWithoutSpace)
                    viewModel.checkEmailExists(emailWithoutSpace)
                    onClickNext = true

                }
            }
        }
        setOnClickListenerForSignInButton()
    }

    private fun checkEmailExists(state: BasicState<Boolean>) {
        if (onClickNext) {
            onClickNext = false
            Log.d("onClickNext", "onClick")
            if (state is BasicState.SuccessState<*>) {
                val isExists = state.data as Boolean
                if (isExists) {
                    //TODO уже существует почта
                } else {
                    val password1 = removeAllSpaces(binding.etPassword.text.toString())
                    val password2 = removeAllSpaces(binding.etPasswordRepeat.text.toString())
                    //TODO сообщить что поле пустое
                    if (password1 == null || password1.isEmpty()) {
                    }
                    if (password2 == null || password2.isEmpty()) {
                    }
                    if (password1 != password2) {
                        //TODO сообщить пользователю, что пароли не совпадают
                    }
                    if (password1 != null && password2 != null && password1.isNotEmpty() && password2.isNotEmpty() && password1 == password2) {
                        viewModel.setPassword(password1)
                        (activity as MainActivity).replaceFragment(NameRegistrationFragment())
                    }
                }
            } else {
                //TODo обработать ошибку сервера
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

    private fun setOnClickListenerForSignInButton() {
        binding.tvSignIn.setOnClickListener {
            (activity as MainActivity).replaceFragment(SignInFragment())
        }
    }

    private fun removeAllSpaces(text: String?) = text?.replace("\\s".toRegex(), "")
}