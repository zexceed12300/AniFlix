package com.zexceed.aniflix.ui.animelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.zexceed.aniflix.adapter.MylistAdapter
import com.zexceed.aniflix.databinding.FragmentAnimelistBinding
import com.zexceed.aniflix.ui.animedetail.AnimeDetailViewModel
import com.zexceed.aniflix.ui.home.complete.CompleteViewModel
import com.zexceed.aniflix.utils.ViewModelFactory

class AnimeListFragment : Fragment() {

    private var _binding: FragmentAnimelistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: AnimeListViewModel
    private lateinit var mAdapter: MylistAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimelistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = obtainViewModel(requireActivity())

        mAdapter = MylistAdapter()

        binding.apply {
            viewModel.getAllMylist().observe(viewLifecycleOwner) { result ->
                mAdapter.submitList(result)
                rvMylist.apply {
                    adapter = mAdapter
                    setHasFixedSize(true)
                }
            }
        }
    }

    private fun obtainViewModel(activity: FragmentActivity): AnimeListViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AnimeListViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}