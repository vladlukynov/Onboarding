package com.src.onboarding.presentation.welcome.name_registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.src.onboarding.databinding.FragmentNameRegistrationBinding

class NameRegistrationFragment : Fragment() {
    private lateinit var binding: FragmentNameRegistrationBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNameRegistrationBinding.inflate(inflater)
        return binding.root
    }
}