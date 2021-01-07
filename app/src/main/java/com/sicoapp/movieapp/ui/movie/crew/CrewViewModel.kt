package com.sicoapp.movieapp.ui.movie.crew

import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.sicoapp.movieapp.data.api.ApiServiceFlowable
import com.sicoapp.movieapp.data.model.response.movie.Movie
import com.sicoapp.movieapp.ui.movie.crew.adapter.CrewAdapter
import com.sicoapp.movieapp.utils.API_KEY
import io.reactivex.schedulers.Schedulers

class CrewViewModel (
    val crewId: Int,
    val api: ApiServiceFlowable
) : BaseObservable() {

    val adapter = CrewAdapter()

    fun rxToLiveData() : LiveData<Movie> {
        val source = LiveDataReactiveStreams.fromPublisher(
            api.loadCrewBy( crewId , API_KEY,)
                .subscribeOn(Schedulers.io())
        )
        return source
    }
}
