package com.sicoapp.movieapp.ui.movie.topmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sicoapp.movieapp.R
import com.sicoapp.movieapp.databinding.FragmentMovieTopBinding
import com.sicoapp.movieapp.repository.RemoteRepository
import com.sicoapp.movieapp.ui.movie.topmovie.adapter.Adapter
import com.sicoapp.movieapp.utils.BindMovie
import com.sicoapp.movieapp.utils.CREW_ID
import com.sicoapp.movieapp.utils.ITEM_ID
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TopMovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieTopBinding

    private var pageId = 10


    lateinit var adapter: Adapter

    @Inject
    lateinit var remoteRepository: RemoteRepository

    private val viewModel by lazy {
        TopMovieViewModel(
            remoteRepository,

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
            },
            )
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMovieTopBinding.inflate(inflater)

        binding.data = viewModel

        observeTopRated()

        scrollRecylerView()

        return binding.root
    }

    private fun observeTopRated() {
        viewModel.topMovies().observe(
            viewLifecycleOwner, Observer {

                val movieResponse = it.getOrNull()

                if (movieResponse != null) {

                    val movieItemsList = movieResponse.results.map { BindMovie(it) }
                    viewModel.adapter.addMovies(movieItemsList)
                }
            }
        )
    }



    private fun scrollRecylerView() {
        binding.recylerViewFragmentTopMovie.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.topMovies().observe(
                        viewLifecycleOwner, Observer {

                            var movieResponse = it.getOrNull()

                            if (movieResponse != null) {

                                val movieItemsList = movieResponse.results.map { BindMovie(it) }
                                viewModel.adapter.addMovies(movieItemsList)
                            }
                        }
                    )
                }
            }
        })
    }
}