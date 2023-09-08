package com.faizi.smallworldtest.ui.popular_movie

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.faizi.smallworldtest.R
import com.faizi.smallworldtest.data.api.MovieDBClient
import com.faizi.smallworldtest.data.api.MovieDBInterface
import com.faizi.smallworldtest.data.repository.NetworkState
import com.faizi.smallworldtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    lateinit var movieRepository: MoviePagedListRepository

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(
            this@MainActivity, R.layout.activity_main
        ) as ActivityMainBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService: MovieDBInterface = MovieDBClient.getClient()

        movieRepository = MoviePagedListRepository(apiService)

        val movieAdapter = PopularMoviePagedListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                return if (viewType == movieAdapter.MOVIE_VIEW_TYPE) 1 /*Movie_VIEW_TYPE will occupy 1 out of 3 span*/ else 3 /*NETWORK_VIEW_TYPE will occupy all 3 span*/
            }
        }

        binding.apply {

            rvMovieList.layoutManager = gridLayoutManager
            rvMovieList.setHasFixedSize(true)
            rvMovieList.adapter = movieAdapter

            viewModel.moviePagedList.observe(this@MainActivity) {
                movieAdapter.submitList(it)
            }

            viewModel.networkState.observe(this@MainActivity) {
                progressBarPopular.visibility =
                    if (viewModel.isEmptyList() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
                txtErrorPopular.visibility =
                    if (viewModel.isEmptyList() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

                if (!viewModel.isEmptyList()) {
                    movieAdapter.setNetworkState(it)
                }
            }
        }

    }

}