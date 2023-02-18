package com.src.onboarding.presentation.hr.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.src.onboarding.databinding.FragmentHrEmployeeProfileBinding

class HrEmployeeProfileFragment : Fragment() {
    lateinit var binding: FragmentHrEmployeeProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHrEmployeeProfileBinding.inflate(inflater)
        return binding.root
    }
}
