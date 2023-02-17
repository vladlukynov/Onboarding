package com.src.onboarding.presentation.welcome.code_enter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.src.onboarding.R
import com.src.onboarding.databinding.FragmentCodeEnterBinding

class CodeEnterFragment : Fragment() {

    lateinit var binding: FragmentCodeEnterBinding
    lateinit var editTextList: List<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCodeEnterBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCodeEditTextListeners()
        setListenerForLastEditText()
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
                Log.d("TEST", "Confirmation code is $code")
                //editTextList[0].text.toString()
                //viewModel.checkRecoveryCode(code = code)
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
}