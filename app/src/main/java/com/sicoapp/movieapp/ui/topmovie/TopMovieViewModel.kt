package com.sicoapp.movieapp.ui.topmovie

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.sicoapp.movieapp.data.remote.response.movie.MovieResponse
import com.sicoapp.movieapp.domain.Repository
import com.sicoapp.movieapp.ui.popular.BindMovie
import com.sicoapp.movieapp.ui.topmovie.adapter.TopMovieAdapter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author ll4
 * @date 12/6/2020
 */
class TopMovieViewModel @ViewModelInject constructor(
    private var repository: Repository
) : ViewModel() {

    var pageId = 1L

    val adapter = TopMovieAdapter()

    init {
        getTopRated()
    }

    fun getTopRated() {
        repository
            .getTopRated(pageId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<MovieResponse> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(response: MovieResponse) {
                        val movieItemsList = response.results.map { BindMovie(it) }
                        adapter.addMovies(movieItemsList)
                        pageId++
                    }

                    override fun onError(e: Throwable) {
                        Log.d("errorTopMovie", "${e.stackTrace}")
                    }

                    override fun onComplete() {
                    }
                }
            )
    }
}


