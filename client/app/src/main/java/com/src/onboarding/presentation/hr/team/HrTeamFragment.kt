package com.src.onboarding.presentation.hr.add_employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.src.onboarding.databinding.FragmentHrTeamBinding

class HrTeamFragment : Fragment() {
    lateinit var binding: FragmentHrTeamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHrTeamBinding.inflate(inflater)
        return binding.root
    }
}
