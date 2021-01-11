package com.sicoapp.movieapp.ui.movie.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.sicoapp.movieapp.data.api.ApiServiceFlowable
import com.sicoapp.movieapp.data.model.response.movie.MovieResponse
import com.sicoapp.movieapp.data.model.response.multi.Multi
import com.sicoapp.movieapp.data.repository.RemoteRepository
import com.sicoapp.movieapp.ui.movie.popular.BindMovie
import com.sicoapp.movieapp.ui.movie.search.adapter.SearchAdapter
import com.sicoapp.movieapp.utils.API_KEY
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author ll4
 * @date 1/4/2021
 */
class SearchViewModel @ViewModelInject constructor(
    private val remoteRepository: RemoteRepository)
    : ViewModel() {

    val adapter = SearchAdapter()

    var pageId = 1


    fun loadRemoteData(query: String) {
        remoteRepository
            .fetchSearchMulti(query, pageId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : SingleObserver<Multi> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onSuccess(response: Multi) {
                        val movieResponse = response.results
                        val movieItemsList = movieResponse.map { BindMulti(it) }
                        adapter.updateItems(movieItemsList)
                        pageId++
                    }

                    override fun onError(e: Throwable) {

                    }

                }
            )
    }
}
