package com.src.onboarding.presentation.support

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.src.onboarding.databinding.FragmentClientSupportBinding

class ClientSupportFragment : Fragment() {
    private lateinit var binding: FragmentClientSupportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClientSupportBinding.inflate(inflater)
        return binding.root
    }
}
