package com.zexceed.aniflix.ui.home.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.zexceed.aniflix.adapter.ScheduleAdapter
import com.zexceed.aniflix.databinding.FragmentScheduleBinding
import com.zexceed.aniflix.respository.Resource
import com.zexceed.aniflix.utils.ViewModelFactory

class FragmentSchedule : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ScheduleViewModel
    private lateinit var mAdapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = obtainViewModel(requireActivity())

        mAdapter = ScheduleAdapter(viewLifecycleOwner, viewModel)

        binding.apply {
            viewModel.schedule.observe(viewLifecycleOwner) {result ->
                when(result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        mAdapter.submitList(result.data.scheduleList)
                        rvSchedule.apply {
                            adapter = mAdapter
                            setHasFixedSize(true)
                        }
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun obtainViewModel(activity: FragmentActivity): ScheduleViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[ScheduleViewModel::class.java]
    }

}