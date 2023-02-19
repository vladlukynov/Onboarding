package com.src.onboarding.presentation.support.appeal_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.src.onboarding.databinding.FragmentAppealPageBinding

class AppealPageFragment : Fragment() {
    private lateinit var binding: FragmentAppealPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppealPageBinding.inflate(inflater)
        return binding.root
    }
}
