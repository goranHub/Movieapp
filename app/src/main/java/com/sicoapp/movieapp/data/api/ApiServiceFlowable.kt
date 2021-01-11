package com.sicoapp.movieapp.data.api

import com.sicoapp.movieapp.data.model.response.movie.IMovie
import com.sicoapp.movieapp.data.model.response.multi.Multi
import com.sicoapp.movieapp.data.model.response.movie.Movie
import com.sicoapp.movieapp.data.model.response.movie.MovieResponse
import com.sicoapp.movieapp.data.model.response.tvShow.TvResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author ll4
 * @date 12/6/2020
 */

interface ApiServiceFlowable {

    @GET("movie/{id}?&append_to_response=credits")
    fun loadCrewBy(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Flowable<Movie>

    @GET("tv/{id}")
    fun getByTvID(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Flowable<TvResponse>

    @GET("movie/{id}")
    fun getByMovieID(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Flowable<Movie>

}
