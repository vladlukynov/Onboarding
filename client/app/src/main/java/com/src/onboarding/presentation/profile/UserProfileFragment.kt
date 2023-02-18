package com.src.onboarding.presentation.profile

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.src.onboarding.R
import com.src.onboarding.databinding.FragmentUserProfileBinding

class UserProfileFragment : Fragment() {
    lateinit var binding: FragmentUserProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackButtonOnClick()
    }

    private fun setBackButtonOnClick() {
        binding.ivBackButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        const val USER_ID = "user_id"
    }
}