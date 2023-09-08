package com.faizi.smallworldtest.ui.movie_details

import androidx.lifecycle.LiveData
import com.faizi.smallworldtest.data.api.MovieDBInterface
import com.faizi.smallworldtest.data.model.MovieDetails
import com.faizi.smallworldtest.data.repository.MovieDetailsNetworkDataSource
import com.faizi.smallworldtest.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailesRepository(private val apiService: MovieDBInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource
    fun fetchSingleMovieDetails(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetails> {

        movieDetailsNetworkDataSource =
            MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse
    }

    fun getMovieDetailsNetworkState():LiveData<NetworkState>{
        return movieDetailsNetworkDataSource.networkState
    }

}