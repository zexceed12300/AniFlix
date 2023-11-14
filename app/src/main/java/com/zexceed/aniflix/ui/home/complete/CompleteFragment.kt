package com.zexceed.aniflix.ui.home.complete

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.zexceed.aniflix.R
import com.zexceed.aniflix.adapter.CompleteAdapter
import com.zexceed.aniflix.databinding.FragmentCompleteBinding
import com.zexceed.aniflix.respository.Resource
import com.zexceed.aniflix.ui.home.ongoing.OngoingViewModel
import com.zexceed.aniflix.utils.ViewModelFactory

class CompleteFragment : Fragment() {

    private var _binding: FragmentCompleteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CompleteViewModel
    private lateinit var mAdapter: CompleteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCompleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = obtainViewModel(requireActivity())
        mAdapter = CompleteAdapter()

        binding.apply {
            setList()

            imgBack.setOnClickListener {
                findNavController().navigate(R.id.action_completeFragment_to_navigation_home)
            }
        }
    }

    private fun setList() {
        viewModel.getComplete(1)
        viewModel.complete.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    mAdapter.submitList(result.data.animeList)
                    binding.rvComplete.apply {
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
    }

    private fun obtainViewModel(activity: FragmentActivity): CompleteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[CompleteViewModel::class.java]
    }

}