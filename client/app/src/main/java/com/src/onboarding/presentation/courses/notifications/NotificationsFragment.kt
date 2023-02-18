package com.src.onboarding.presentation.courses.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.src.onboarding.databinding.FragmentNotificationsBinding
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.domain.user.Notification
import com.src.onboarding.presentation.MainActivity
import com.src.onboarding.presentation.courses.notifications.adapter.NotificationAdapter
import com.src.onboarding.presentation.courses.notifications.viewModel.NotificationViewModel

class NotificationsFragment : Fragment() {
    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var viewModel: NotificationViewModel
    private var notifications: ArrayList<Notification> = ArrayList(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater)
        viewModel = (activity as MainActivity).getNotificationViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapterForNotifications()
        viewModel.liveDataGetNotificationsState.observe(
            this.viewLifecycleOwner, this::checkGetNotificationsState
        )
        viewModel.liveDataClearNotificationState.observe(
            this.viewLifecycleOwner, this::checkClearNotificationState
        )
        viewModel.getNotifications()
        setOnClickListenerForRemoveAllNotification()
    }

    private fun setAdapterForNotifications() {
        val adapter = NotificationAdapter()
        val layoutManager = GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        binding.rvNotifications.layoutManager = layoutManager
        binding.rvNotifications.adapter = adapter
    }

    private fun checkGetNotificationsState(state: BasicState<List<Notification>>) {
        when (state) {
            is BasicState.SuccessState -> {
                setDataForRecycler(state.data)
            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {

            }
        }

    }

    private fun setDataForRecycler(notifications: List<Notification>) {
        this.notifications = ArrayList(notifications)
        val adapter = binding.rvNotifications.adapter as NotificationAdapter
        adapter.submitList(this.notifications)
    }


    private fun checkClearNotificationState(state: BasicState<Unit>) {
        when (state) {
            is BasicState.SuccessState -> {
            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {

            }
        }
    }

    private fun setOnClickListenerForRemoveAllNotification() {
        binding.ivTrashCan.setOnClickListener {
            val notificationSize = notifications.size
            notifications.removeAll(notifications.toSet())
            val adapter = binding.rvNotifications.adapter as NotificationAdapter
            adapter.notifyItemRangeRemoved(0, notificationSize)
            viewModel.clearNotifications()
        }
    }
}