package com.src.onboarding.presentation.welcome.code_enter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.src.onboarding.R
import com.src.onboarding.databinding.FragmentCodeEnterBinding
import com.src.onboarding.databinding.FragmentLoadingBinding
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.state.login.CodeState
import com.src.onboarding.presentation.LoginActivity
import com.src.onboarding.presentation.welcome.registration.viewModel.RegistrationViewModel

class CodeEnterFragment : Fragment() {

    lateinit var binding: FragmentCodeEnterBinding
    private lateinit var viewModel: RegistrationViewModel
    private lateinit var bindingLoading: FragmentLoadingBinding
    lateinit var editTextList: List<EditText>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCodeEnterBinding.inflate(inflater)
        viewModel = (activity as LoginActivity).getRegistrationViewModel()
        bindingLoading = binding.loading
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveDataIsLoading.observe(
            this.viewLifecycleOwner, this::checkLoading
        )
        viewModel.liveDataCodeState.observe(
            this.viewLifecycleOwner, this::checkState
        )
        viewModel.liveDataRepeatingCodeState.observe(
            this.viewLifecycleOwner, this::checkSendRepeatingCodeState
        )
        setCodeEditTextListeners()
        setListenerForLastEditText()
        setCodeEditTextListeners()
        setListenerForLastEditText()
        setOnClickListenerForRepeatingCode()
    }

    private fun setListenerForLastEditText() {
        binding.etCode4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                var code = ""
                for (i in editTextList) {
                    code += i.text.toString()
                }
                editTextList[0].text.toString()
                viewModel.checkRecoveryCode(code = code)
            }
        })
    }

    private fun setCodeEditTextListeners() {
        with(binding) {
            editTextList = listOf(
                etCode1,
                etCode2,
                etCode3,
                etCode4
            )
        }

        for (i in editTextList.indices) {
            editTextList[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    if (p0 != null) {
                        if (p0.length == 1 && i != editTextList.lastIndex) {
                            editTextList[i + 1].requestFocus()
                            setEditTextFilledSymbolStyle(editTextList[i])
                        } else if (p0.isEmpty() && i != 0) {
                            editTextList[i - 1].requestFocus()
                            setEditTextEmptySymbolStyle(editTextList[i])
                        } else if (i == 0) {
                            setEditTextEmptySymbolStyle(editTextList[i])
                        } else if (i == editTextList.lastIndex) {
                            setEditTextFilledSymbolStyle(editTextList[i])
                        }
                    }
                }
            })
        }
    }

    private fun setEditTextEmptySymbolStyle(et: EditText) {
        et.textSize = 28F
        et.background =
            activity?.let { ContextCompat.getDrawable(it, R.drawable.enter_code_number_background) }
    }

    private fun setEditTextFilledSymbolStyle(et: EditText) {
        et.textSize = 32F
        et.background = null
    }

    private fun checkLoading(isLoading: Boolean) {
        if (isLoading) {
            bindingLoading.clLoadingPage.visibility = View.VISIBLE
        } else {
            bindingLoading.clLoadingPage.visibility = View.GONE
        }
    }

    private fun checkState(state: CodeState) {
        when (state) {
            is CodeState.SuccessState -> {
                //TODO перейти на новый фрагмент
            }
            is CodeState.WrongCodeState -> {
                viewModel.setDefaultValueForCodeState()
                (activity as LoginActivity).showSnackBar(getString(R.string.invalid_code))
            }
            is CodeState.ErrorState -> {
                viewModel.setDefaultValueForCodeState()
                (activity as LoginActivity).showSnackBar(null)
            }
            else -> {}
        }
    }

    private fun checkSendRepeatingCodeState(state: BasicState<Unit>) {
        when (state) {
            is BasicState.SuccessState -> {
                println("код отправлен")
            }
            is BasicState.ErrorState -> {
                (activity as LoginActivity).showSnackBar(null)
            }
            else -> {}
        }
    }

    private fun setOnClickListenerForRepeatingCode() {
        binding.tvRepeatingCode.setOnClickListener {
            viewModel.sendRepeatingCode()
        }
    }
}