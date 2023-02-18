package com.src.onboarding.presentation.courses.tasks

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.src.onboarding.R
import com.src.onboarding.databinding.FragmentTasksBinding
import com.src.onboarding.domain.model.task.TaskWithTitle
import com.src.onboarding.domain.state.login.BasicState
import com.src.onboarding.presentation.MainActivity
import com.src.onboarding.presentation.courses.tasks.adapter.TasksAdapter
import com.src.onboarding.presentation.courses.tasks.viewModel.TasksViewModel

class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding
    private lateinit var viewModel: TasksViewModel
    private var tasks: ArrayList<TaskWithTitle> = ArrayList(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater)
        viewModel = (activity as MainActivity).getTasksViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapterForRecyclerView()
        viewModel.liveDataGetTasksState.observe(
            this.viewLifecycleOwner, this::checkGetTasksState
        )
        viewModel.liveDataSetCompletedTaskState.observe(
            this.viewLifecycleOwner, this::checkSetCompletedTaskState
        )
        viewModel.getTasks()
    }

    private fun setAdapterForRecyclerView() {
        val adapter = TasksAdapter { item -> onCLickTaskCompleted(item) }
        val layoutManager = GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        binding.rvTasks.layoutManager = layoutManager
        binding.rvTasks.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onCLickTaskCompleted(taskModel: TaskWithTitle.TaskModel) {
        viewModel.setCompletedForTask(taskId = taskModel.id, completed = !taskModel.completed)
        val completed = taskModel.completed
        taskModel.completed = !taskModel.completed
        tasks.remove(taskModel)
        if (completed) {
            if (tasks[0] is TaskWithTitle.TitleModel &&
                (tasks[0] as TaskWithTitle.TitleModel).title == getString(R.string.unfinished)
            ) {
                var lastPositionUncompletedTasks = 0
                for (i in (1 until tasks.size)) {
                    if (tasks[i] is TaskWithTitle.TitleModel) {
                        lastPositionUncompletedTasks = i
                        break
                    }
                }
                tasks.add(lastPositionUncompletedTasks, taskModel)

            } else {
                tasks.add(0, TaskWithTitle.TitleModel(getString(R.string.unfinished)))
                tasks.add(1, taskModel)
            }
            if (tasks[tasks.size - 1] is TaskWithTitle.TitleModel) {
                tasks.removeAt(tasks.size - 1)
            }
        } else {
            if (tasks.size == 1) {
                tasks.removeAt(0)
                tasks.add(TaskWithTitle.TitleModel(getString(R.string.finished)))
            } else if (tasks[0] is TaskWithTitle.TitleModel && tasks[1] is TaskWithTitle.TitleModel) {
            } else {
                var indexOFFinishedTitle = 0
                for (i in (1 until tasks.size)) {
                    if (tasks[i] is TaskWithTitle.TitleModel && (tasks[i] as TaskWithTitle.TitleModel).title == getString(
                            R.string.finished
                        )
                    ) {
                        indexOFFinishedTitle = i
                        break
                    }
                }
                if (indexOFFinishedTitle == 0) {
                    tasks.add(TaskWithTitle.TitleModel(getString(R.string.finished)))
                }
            }
            tasks.add(taskModel)
        }
        val adapter = binding.rvTasks.adapter as TasksAdapter
        adapter.notifyDataSetChanged()
    }

    private fun setDataForAdapter(data: List<TaskWithTitle>) {

        tasks = ArrayList(data)
        val adapter = binding.rvTasks.adapter as TasksAdapter
        adapter.submitList(tasks)
    }

    private fun checkGetTasksState(state: BasicState<List<TaskWithTitle>>) {
        when (state) {
            is BasicState.SuccessState -> {
                setDataForAdapter(state.data)
            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {

            }
        }
    }

    private fun checkSetCompletedTaskState(state: BasicState<Unit>) {
        when (state) {
            is BasicState.SuccessState -> {

            }
            is BasicState.LoadingState -> {}
            is BasicState.ErrorState -> {

            }
        }
    }
}
