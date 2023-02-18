package com.src.onboarding.presentation.hr.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.src.onboarding.databinding.FragmentHrTeamBinding
import com.src.onboarding.domain.model.hr.teammate.Teammate
import com.src.onboarding.presentation.HrActivity
import com.src.onboarding.presentation.hr.add_task.AddTaskFragment
import com.src.onboarding.presentation.hr.team.adapter.TeammateAdapter

class HrTeamFragment : Fragment() {
    private lateinit var binding: FragmentHrTeamBinding
    private var teammates: ArrayList<Teammate> = ArrayList(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHrTeamBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapterForNotifications()
        setOnAddButtonClickListener()
    }

    private fun setAdapterForNotifications() {
        binding.rvColleagues.adapter = TeammateAdapter()
        binding.rvColleagues.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
    }

    private fun setDataForRecycler(teammates: List<Teammate>) {
        this.teammates = java.util.ArrayList(teammates)
        val adapter = binding.rvColleagues.adapter as TeammateAdapter
        adapter.submitList(this.teammates)
    }

    private fun setOnAddButtonClickListener() {
        binding.cvAddTask.setOnClickListener {
            (activity as HrActivity).replaceFragment(AddTaskFragment())
        }
    }
}
