package com.sicoapp.movieapp.ui.movie.popular

import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.sicoapp.movieapp.data.model.response.movie.MovieResponse
import com.sicoapp.movieapp.data.repository.RemoteRepository
import com.sicoapp.movieapp.ui.movie.topmovie.adapter.TopMovieAdapter
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author ll4
 * @date 12/6/2020
 */
class PopularViewModel @ViewModelInject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    var pageId = 1
    val adapter = TopMovieAdapter()

    init {
        loadRemoteData()
    }

     fun loadRemoteData() {
        remoteRepository
            .fetchPopularMovies(pageId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : SingleObserver<MovieResponse> {
                    override fun onSubscribe(d: Disposable) {
                    }
                    override fun onSuccess(response: MovieResponse) {
                        val movieItemsList = response.results.map { BindMovie(it) }
                        adapter.addMovies(movieItemsList)
                        pageId++
                    }
                    override fun onError(e: Throwable) {
                        Log.d("error", "${e.stackTrace}")
                    }
                }
            )
    }
}


