package com.sicoapp.movieapp.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sicoapp.movieapp.R
import com.sicoapp.movieapp.databinding.FragmentMoviePopularBinding
import com.sicoapp.movieapp.databinding.ItemMoviePopularBinding
import com.sicoapp.movieapp.ui.BaseFragment
import com.sicoapp.movieapp.ui.popular.adapter.PopularMovieAdapter
import com.sicoapp.movieapp.utils.CREW_ID
import com.sicoapp.movieapp.utils.ITEM_ID
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author ll4
 * @date 1/1/2021
 */
@AndroidEntryPoint
class PopularMovieFragment : BaseFragment() {

    private lateinit var binding: FragmentMoviePopularBinding

    private val viewModel: PopularViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMoviePopularBinding.inflate(inflater)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@PopularMovieFragment.viewModel
        }

        viewModel.adapter.listenerCall = object : PopularMovieAdapter.ListenerCall{
            override fun callback(binding: ItemMoviePopularBinding) {
                binding.apply {
                    popularMovieFragment = this@PopularMovieFragment
                }
            }
        }

        scrollRecyclerView()

        return binding.root
    }


    fun openItem(movieId :Long){
        val bundleItemId = bundleOf(ITEM_ID to movieId)
        findNavController().navigate(
            R.id.action_popularMovieFragment_to_movieDetailsFragment,
            bundleItemId
        )
    }

    fun openCrew(crewId :Long){
        val bundleCrewId = bundleOf(CREW_ID to crewId)
        findNavController().navigate(
            R.id.action_popularMovieFragment_to_crewMovieFragment,
            bundleCrewId
        )
    }

    private fun scrollRecyclerView() {
        binding.recylerViewFragmentTopMovie.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.getPopular()
                }
            }
        })
    }

}