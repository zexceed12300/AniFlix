package com.zexceed.aniflix.ui.home.complete

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.zexceed.aniflix.R
import com.zexceed.aniflix.adapter.AnimeLoadStateAdapter
import com.zexceed.aniflix.adapter.CompletePagingAdapter
import com.zexceed.aniflix.databinding.FragmentCompleteBinding
import com.zexceed.aniflix.respository.Resource
import com.zexceed.aniflix.utils.ViewModelFactory

class CompleteFragment : Fragment() {

    private var _binding: FragmentCompleteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CompleteViewModel
    private lateinit var mAdapter: CompletePagingAdapter

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
        mAdapter = CompletePagingAdapter()

        binding.apply {
            setList()

            imgBack.setOnClickListener {
                findNavController().navigate(R.id.action_completeFragment_to_navigation_home)
            }
        }
    }

    private fun setList() {
        binding.apply {
            val footerAdapter = AnimeLoadStateAdapter {
                mAdapter.retry()
            }
            val layoutManager = GridLayoutManager(requireActivity(), 3)
            rvComplete.apply {
                setHasFixedSize(true)
                adapter = mAdapter.withLoadStateFooter(
                    footer = footerAdapter
                )
                rvComplete.layoutManager = layoutManager
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

            viewModel.complete.observe(viewLifecycleOwner) { result ->
                mAdapter.submitData(lifecycle, result)
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