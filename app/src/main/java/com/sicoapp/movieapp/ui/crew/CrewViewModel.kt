package com.sicoapp.movieapp.ui.crew

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.sicoapp.movieapp.data.remote.response.movie.Movie
import com.sicoapp.movieapp.domain.IRepository
import com.sicoapp.movieapp.ui.crew.adapter.CrewAdapter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CrewViewModel @ViewModelInject constructor(
    private val repository: IRepository
) : ViewModel() {

    val adapter = CrewAdapter()

    fun loadRemoteDataCrew(movieId: Long) {
        repository
            .fetchCrew(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(

                object : Observer<Movie> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(response: Movie) {
                        val list = response.credits?.cast
                            ?.filter { !response.posterPath.isNullOrBlank() }
                            ?.distinctBy { it.profilePath }
                            ?.map { CrewObservable(it) }
                        adapter.addCast(list)
                    }

                    override fun onError(e: Throwable) {
                        Log.d("error", "${e.stackTrace}")
                    }

                    override fun onComplete() {
                    }

                }
            )
    }
}
