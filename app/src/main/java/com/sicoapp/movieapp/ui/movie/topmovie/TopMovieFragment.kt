package com.sicoapp.movieapp.ui.movie.topmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sicoapp.movieapp.R
import com.sicoapp.movieapp.data.api.MovieApiService
import com.sicoapp.movieapp.databinding.FragmentMovieTopBinding
import com.sicoapp.movieapp.utils.BindMovie
import com.sicoapp.movieapp.utils.CREW_ID
import com.sicoapp.movieapp.utils.ITEM_ID
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TopMovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieTopBinding

    @Inject
    lateinit var api: MovieApiService

    private val viewModel by lazy {
        TopMovieViewModel(
            api,
            {
                postID ->
            val bundleItemId = bundleOf(ITEM_ID to postID)
            findNavController().navigate(
                R.id.action_movieListFragment_to_movieDetailsFragment,
                bundleItemId
            )
            },
            {
                crewID ->
            val bundleCrewId = bundleOf(CREW_ID to crewID)
            findNavController().navigate(
                R.id.action_movieListFragment_to_crewMovieFragment,
                bundleCrewId
            )
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMovieTopBinding.inflate(inflater)
        binding.data = viewModel

        init()
        scrollRecylerView()

        return binding.root
    }

    private fun init() {
        viewModel.rxToLiveData().observe(
            viewLifecycleOwner, {
                val movieResponse = it.results
                val movieItemsList = movieResponse.map { BindMovie(it) }
                viewModel.adapter.addMovies(movieItemsList)
            }
        )
    }

    private fun scrollRecylerView() {

        binding.recylerViewFragmentTopMovie.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.rxToLiveData().observe(
                        viewLifecycleOwner, {
                            val movieResponse = it.results
                            val movieItemsList = movieResponse.map { BindMovie(it) }
                            viewModel.adapter.addMovies(movieItemsList)
                        }
                    )
                }
            }
        })
    }
}