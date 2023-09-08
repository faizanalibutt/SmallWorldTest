package com.faizi.smallworldtest.ui.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.faizi.smallworldtest.data.model.MovieDetails
import com.faizi.smallworldtest.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(private val movieRepository: MovieDetailesRepository, movieId: Int) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var movieId: Int = -1

    val movieDetails: LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    fun setMovieId(movieId: Int) {
        this.movieId = movieId
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()

    }
}