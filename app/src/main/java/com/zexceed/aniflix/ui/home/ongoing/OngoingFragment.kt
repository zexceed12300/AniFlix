package com.zexceed.aniflix.ui.home.ongoing

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.zexceed.aniflix.R
import com.zexceed.aniflix.adapter.OngoingAdapter
import com.zexceed.aniflix.databinding.FragmentOngoingBinding
import com.zexceed.aniflix.respository.Resource
import com.zexceed.aniflix.ui.home.HomeViewModel
import com.zexceed.aniflix.utils.ViewModelFactory

class OngoingFragment : Fragment() {

    private var _binding: FragmentOngoingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: OngoingViewModel
    private lateinit var mAdapter: OngoingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOngoingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = obtainViewModel(requireActivity())

        mAdapter = OngoingAdapter()

        binding.apply {
            setList()

            imgBack.setOnClickListener {
                findNavController().navigate(R.id.action_ongoingFragment_to_navigation_home)
            }

        }
    }

    private fun setList() {
        viewModel.getOngoing(1)
        viewModel.ongoing.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    mAdapter.submitList(result.data.animeList)
                    binding.rvOngoing.apply {
                        adapter = mAdapter
                        setHasFixedSize(true)
                    }
                }
                is Resource.Error -> {

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun obtainViewModel(activity: FragmentActivity): OngoingViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[OngoingViewModel::class.java]
    }
}