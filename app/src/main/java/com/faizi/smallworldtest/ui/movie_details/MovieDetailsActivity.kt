package com.faizi.smallworldtest.ui.movie_details

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.faizi.smallworldtest.R
import com.faizi.smallworldtest.data.api.MovieDBClient
import com.faizi.smallworldtest.data.api.MovieDBInterface
import com.faizi.smallworldtest.data.api.POSTER_BASE_URL
import com.faizi.smallworldtest.data.model.MovieDetails
import com.faizi.smallworldtest.data.repository.NetworkState
import com.faizi.smallworldtest.databinding.ActivityMovieDetailsBinding
import java.text.NumberFormat
import java.util.Locale

class MovieDetailsActivity : AppCompatActivity() {

    private val viewModel: SingleMovieViewModel by viewModels()

    private lateinit var movieRepository: MovieDetailesRepository

    private val binding: ActivityMovieDetailsBinding by lazy {
        DataBindingUtil.setContentView(
            this@MovieDetailsActivity,
            R.layout.activity_main
        ) as ActivityMovieDetailsBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movieId: Int = intent.getIntExtra("id", 1)
        viewModel.setMovieId(movieId)

        val apiService: MovieDBInterface = MovieDBClient.getClient()
        movieRepository = MovieDetailesRepository(apiService)

        viewModel.movieDetails.observe(this@MovieDetailsActivity) {
            bindUI(it)
        }

        binding.apply {
            viewModel.networkState.observe(this@MovieDetailsActivity) {
                progressBar.visibility =
                    if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
                progressBar.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
            }
        }
    }

    private fun bindUI(it: MovieDetails) {
        binding.apply {
            movieTitle.text = it.title
            movieTagline.text = it.tagline
            movieReleaseDate.text = it.releaseDate
            movieRating.text = it.rating.toString()
            (it.runtime.toString() + " minutes").also { movieRuntime.text = it }
            movieOverview.text = it.overview

            val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
            movieBudget.text = formatCurrency.format(it.budget)
            movieRevenue.text = formatCurrency.format(it.revenue)

            val moviePosterURL = POSTER_BASE_URL + it.posterPath
            Glide.with(this@MovieDetailsActivity).load(moviePosterURL).into(ivMoviePoster)
        }
    }

}