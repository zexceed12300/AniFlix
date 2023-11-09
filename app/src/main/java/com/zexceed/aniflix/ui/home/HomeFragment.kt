package com.zexceed.aniflix.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.zexceed.aniflix.adapter.CompleteAdapter
import com.zexceed.aniflix.adapter.OngoingAdapter
import com.zexceed.aniflix.databinding.FragmentHomeBinding
import com.zexceed.aniflix.models.remote.response.home.Complete
import com.zexceed.aniflix.models.remote.response.home.OnGoing
import com.zexceed.aniflix.respository.Resource
import com.zexceed.aniflix.ui.search.SearchActivity
import com.zexceed.aniflix.utils.Constants.TAG
import com.zexceed.aniflix.utils.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var ongoingAdapter: OngoingAdapter
    private lateinit var completeAdapter: CompleteAdapter

    private var listOngoing: List<OnGoing> = listOf()
    private var listCompleted: List<Complete> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = obtainViewModel(requireActivity())

        binding.apply {

            ongoingAdapter = OngoingAdapter()
            completeAdapter = CompleteAdapter()

            setList()

            searchBar.setOnClickListener {
                val intent = Intent(requireActivity(), SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setList() {
        viewModel.home.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    listOngoing = result.data.home.on_going
                    listCompleted = result.data.home.complete

                    ongoingAdapter.submitList(listOngoing)
                    binding.rvOngoing.apply {
                        adapter = ongoingAdapter
                        setHasFixedSize(true)
                    }
                    completeAdapter.submitList(listCompleted)
                    binding.rvComplete.apply {
                        adapter = completeAdapter
                        setHasFixedSize(true)
                    }
                }

                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    Log.d(TAG, "setList::::::: ${result.error}")
                }
            }
        }
    }

    private fun obtainViewModel(homeActivity: FragmentActivity): HomeViewModel {
        val factory = ViewModelFactory.getInstance(homeActivity.application)
        return ViewModelProvider(homeActivity, factory)[HomeViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}