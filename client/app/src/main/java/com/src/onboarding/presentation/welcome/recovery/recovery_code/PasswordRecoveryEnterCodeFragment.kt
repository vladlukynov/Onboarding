package com.src.onboarding.presentation.welcome.recovery.recovery_code

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.src.onboarding.R
import com.src.onboarding.databinding.FragmentLoadingBinding
import com.src.onboarding.databinding.FragmentPasswordRecoveryEnterCodeBinding
import com.src.onboarding.domain.state.login.CodeState
import com.src.onboarding.presentation.LoginActivity
import com.src.onboarding.presentation.welcome.recovery.recovery.PasswordRecoveryFragment
import com.src.onboarding.presentation.welcome.recovery.recovery_code.viewModel.PasswordRecoveryEnterCodeViewModel

class PasswordRecoveryEnterCodeFragment : Fragment() {
    private lateinit var binding: FragmentPasswordRecoveryEnterCodeBinding
    private lateinit var viewModel: PasswordRecoveryEnterCodeViewModel
    private lateinit var bindingLoading: FragmentLoadingBinding
    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = this.arguments
        //TODO обработка ошибки если имейл не получен
        if (args?.getString(BUNDLE_EMAIL) != null) {
            email = args.getString(BUNDLE_EMAIL) as String
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordRecoveryEnterCodeBinding.inflate(inflater)
        bindingLoading = binding.loading
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as LoginActivity).getPasswordRecoveryEnterCodeViewModel()
        viewModel.liveDataIsLoading.observe(
            this.viewLifecycleOwner, this::checkLoading
        )
        viewModel.liveDataCodeState.observe(
            this.viewLifecycleOwner, this::checkState
        )
        viewModel.liveDataRepeatingCodeState.observe(
            this.viewLifecycleOwner, this::checkSendRepeatingCodeState
        )
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        setOnClickListenerForContinueButton()
        setOnClickListenerForRepeatingCode()
    }

    private fun setOnClickListenerForContinueButton() {
        binding.tvContinueButton.setOnClickListener {
            val code = binding.etCode.text.toString()
            if (code.isEmpty()) {
                binding.tvWrongCode.text = getString(R.string.fill_field)
                binding.tvWrongCode.visibility = View.VISIBLE
            } else {
                binding.tvWrongCode.visibility = View.GONE
            }
            viewModel.checkRecoveryCode(code = code, email = email)
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
    private fun checkState(state: CodeState) {
        when (state) {
            is CodeState.SuccessState -> {
                val bundle = Bundle()
                bundle.putString(PasswordRecoveryFragment.BUNDLE_EMAIL, email)
                val fragment = PasswordRecoveryFragment()
                fragment.arguments = bundle
                (activity as LoginActivity).replaceFragment(fragment)
            }
            is CodeState.WrongCodeState -> {
                viewModel.setDefaultValueForCodeState()
                binding.tvWrongCode.visibility = View.VISIBLE
            }
            is CodeState.ErrorState -> {
                viewModel.setDefaultValueForCodeState()
                println("error")
                binding.tvWrongCode.visibility = View.VISIBLE
            }
            else -> {}
        }
    }

    //TODO обработать ошибки (повторная отправка кода)
    private fun checkSendRepeatingCodeState(state: CodeState) {
        when (state) {
            is CodeState.SuccessState -> {
                println("код отправлен")
            }
            else -> {
                println("ошибка")
            }
        }
    }

    private fun setOnClickListenerForRepeatingCode() {
        binding.tvRepeatingCode.setOnClickListener {
            viewModel.sendRepeatingCodeState(email)
        }
    }

    companion object {
        const val BUNDLE_EMAIL = "email"
    }
}