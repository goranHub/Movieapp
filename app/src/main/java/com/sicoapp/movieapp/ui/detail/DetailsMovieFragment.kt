package com.sicoapp.movieapp.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.hsalf.smileyrating.SmileyRating
import com.sicoapp.movieapp.EntryActivity
import com.sicoapp.movieapp.R
import com.sicoapp.movieapp.databinding.FragmentMovieDetailsBinding
import com.sicoapp.movieapp.utils.ITEM_ID
import com.sicoapp.movieapp.utils.MEDIATYP
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_entry.*

@AndroidEntryPoint
class DetailsMovieFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: FragmentMovieDetailsBinding
    var movieId = 0L
    var mediaTyp = ""


    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getLong(ITEM_ID, -1)?.let {
            movieId = it
        }
        arguments?.getString(MEDIATYP, "")?.let {
            mediaTyp = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMovieDetailsBinding.inflate(inflater)

        if ((mediaTyp == "movie") or (mediaTyp == "")) {
            updateUIMovie(movieId)
        } else {
            updateUITv(movieId)
        }

        binding.data = viewModel.bindDetails

        saveIntoDB()

        callFromDB()

        binding.btnDeleteAll.setOnClickListener {
            deleteFromDB()
        }

        setNavigationViewListener()

        return binding.root
    }

    private fun deleteFromDB() {
        viewModel.removeRatingForMovie(movieId.toInt())
    }

    private fun saveIntoDB() {
        binding.smiley.setSmileySelectedListener {
            viewModel.insertData(movieId.toInt(), it.rating)
        }
    }

    private fun callFromDB() {

        viewModel.getSavedSmileyDetails(movieId.toInt())
            .observe(viewLifecycleOwner, { response ->
                if (response != null) {
                    val rating = response.rating
                    setFaceBackgroundColor(rating)
                }
            })
    }

    private fun setFaceBackgroundColor(
        rating: Int
    ) {
        lateinit var type: SmileyRating.Type
        var color = Color.YELLOW
        if (rating == 1) {
            type = SmileyRating.Type.TERRIBLE
            binding.smiley.setRating(rating, false)
            binding.smiley.setFaceBackgroundColor(type, color)
        }
        if (rating == 2) {
            type = SmileyRating.Type.BAD
            binding.smiley.setRating(rating, false)
            binding.smiley.setFaceBackgroundColor(type, color)
        }
        if (rating == 3) {
            type = SmileyRating.Type.OKAY
            binding.smiley.setRating(rating, false)
            binding.smiley.setFaceBackgroundColor(type, color)
        }
        if (rating == 4) {
            type = SmileyRating.Type.GOOD
            binding.smiley.setRating(rating, false)
            binding.smiley.setFaceBackgroundColor(type, color)
        }
        if (rating == 5) {
            type = SmileyRating.Type.GREAT
            binding.smiley.setRating(rating, false)
            binding.smiley.setFaceBackgroundColor(type, color)
        }
    }

    private fun updateUITv(movieId: Long) {
        viewModel.loadRemoteDataTv(movieId)
    }

    private fun updateUIMovie(movieId: Long) {
        viewModel.loadRemoteDataMovie(movieId)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.my_profile -> {
            }

            R.id.list_movie_saved -> {
                findNavController().navigate(
                    R.id.action_movieDetailsFragment_to_savedFragment
                )
            }

            R.id.sign_out -> {
                FirebaseAuth.getInstance().signOut()
                findNavController().navigate(
                    R.id.action_movieDetailsFragment_to_introFragment)
            }
        }

        (activity as EntryActivity).drawer_layout?.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setNavigationViewListener() {
        val navigationView = (activity as EntryActivity).navigation_view
        navigationView.setNavigationItemSelectedListener(this)
    }
}


