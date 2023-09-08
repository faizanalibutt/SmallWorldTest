package com.faizi.smallworldtest.data.api

import com.faizi.smallworldtest.data.model.MovieDetails
import com.faizi.smallworldtest.data.model.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBInterface {

    //https://api.themoviedb.org/3/movie/550?api_key=***
    //https://api.themoviedb.org/3/movie/popular?api_key=***
    //https://api.themoviedb.org/3/

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>


    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

    // filter by name needs to be added.
}