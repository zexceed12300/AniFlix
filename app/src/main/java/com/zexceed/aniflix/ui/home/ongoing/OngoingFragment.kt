package com.zexceed.aniflix.ui.home.ongoing

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.zexceed.aniflix.R
import com.zexceed.aniflix.adapter.AnimeLoadStateAdapter
import com.zexceed.aniflix.adapter.OngoingPagingAdapter
import com.zexceed.aniflix.databinding.FragmentOngoingBinding
import com.zexceed.aniflix.respository.Resource
import com.zexceed.aniflix.utils.ViewModelFactory

class OngoingFragment : Fragment() {

    private var _binding: FragmentOngoingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: OngoingViewModel
    private lateinit var mAdapter: OngoingPagingAdapter

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

        mAdapter = OngoingPagingAdapter()

        binding.apply {
            setList()

            imgBack.setOnClickListener {
                findNavController().navigate(R.id.action_ongoingFragment_to_navigation_home)
            }

        }
    }

    private fun setList() {
        binding.apply {
            val footerAdapter = AnimeLoadStateAdapter {
                mAdapter.retry()
            }
            val layoutManager = GridLayoutManager(requireActivity(), 3)
            rvOngoing.apply {
                setHasFixedSize(true)
                adapter = mAdapter.withLoadStateFooter(
                    footer = footerAdapter
                )
                rvOngoing.layoutManager = layoutManager
            }
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == mAdapter.itemCount && footerAdapter.itemCount > 0) {
                        3
                    } else {
                        1
                    }
                }
            }

            viewModel.ongoing.observe(viewLifecycleOwner) { result ->
                mAdapter.submitData(lifecycle, result)
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