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
import androidx.navigation.fragment.findNavController
import com.zexceed.aniflix.R
import com.zexceed.aniflix.adapter.GenreAdapter
import com.zexceed.aniflix.adapter.HomeCompleteAdapter
import com.zexceed.aniflix.adapter.HomeOngoingAdapter
import com.zexceed.aniflix.databinding.FragmentHomeBinding
import com.zexceed.aniflix.models.remote.response.home.Complete
import com.zexceed.aniflix.models.remote.response.home.OnGoing
import com.zexceed.aniflix.respository.Resource
import com.zexceed.aniflix.ui.home.genre.GenreActivity
import com.zexceed.aniflix.ui.home.genre.GenreActivity.Companion.GENRE_ID
import com.zexceed.aniflix.ui.home.genre.GenreActivity.Companion.GENRE_TITLE
import com.zexceed.aniflix.ui.search.SearchActivity
import com.zexceed.aniflix.utils.Constants.TAG
import com.zexceed.aniflix.utils.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var ongoingAdapter: HomeOngoingAdapter
    private lateinit var completeAdapter: HomeCompleteAdapter
    private lateinit var genreAdapter: GenreAdapter

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
        ongoingAdapter = HomeOngoingAdapter()
        completeAdapter = HomeCompleteAdapter()
        genreAdapter = GenreAdapter(
            onClick = { data ->
                val intent = Intent(requireActivity(), GenreActivity::class.java)
                intent.putExtra(GENRE_TITLE, data.genre_title)
                intent.putExtra(GENRE_ID, data.genre_id)
                startActivity(intent)
            }
        )

        binding.apply {

            setList()

            setGenre()

            searchBar.setOnClickListener {
                val intent = Intent(requireActivity(), SearchActivity::class.java)
                startActivity(intent)
            }

            btnAllOngoing.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_ongoingFragment2)
            }

            btnAllComplete.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_completeFragment)
            }
        }
    }

    private fun setGenre() {
        binding.apply {
            viewModel.genre.observe(viewLifecycleOwner) { result ->
                when(result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        genreAdapter.submitList(result.data.genreList)
                        rvGenre.apply {
                            adapter = genreAdapter
                            setHasFixedSize(true)
                        }
                    }
                    is Resource.Error -> {

                    }
                }
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