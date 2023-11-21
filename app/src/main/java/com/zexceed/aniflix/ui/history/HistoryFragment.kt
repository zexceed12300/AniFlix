package com.zexceed.aniflix.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.zexceed.aniflix.adapter.HistoryAdapter
import com.zexceed.aniflix.databinding.FragmentHistoryBinding
import com.zexceed.aniflix.ui.animelist.AnimeListViewModel
import com.zexceed.aniflix.utils.Constants.TAG
import com.zexceed.aniflix.utils.ViewModelFactory
import java.util.Collections

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: HistoryViewModel
    private lateinit var mAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = obtainViewModel(requireActivity())

        mAdapter = HistoryAdapter()

        binding.apply {
            viewModel.getAllHistory().observe(viewLifecycleOwner) { result ->
                Collections.reverse(result)
                mAdapter.submitList(result)
                rvHistory.apply {
                    adapter = mAdapter
                    setHasFixedSize(true)
                }
            }
        }
    }

    private fun obtainViewModel(activity: FragmentActivity): HistoryViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[HistoryViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}