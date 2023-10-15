package com.xsis.netplix.ui.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.core.R
import com.xsis.core.data.Resource
import com.xsis.core.databinding.LayoutDetailMovieBinding
import com.xsis.core.domain.model.MovieModel
import com.xsis.core.ui.MovieListAdapter
import com.xsis.core.ui.ReviewMovieAdapter
import com.xsis.core.utils.Config
import com.xsis.core.utils.Utils.toList
import com.xsis.core.utils.Utils.updateUI
import com.xsis.netplix.App
import com.xsis.netplix.ui.MovieViewModel

object ShowUI {
    fun getMovies(
        activity: Activity,
        viewModel: MovieViewModel,
        livecycleOwner: LifecycleOwner,
        movieAdapter: MovieListAdapter,
        reviewMovieAdapter: ReviewMovieAdapter,
        progressBar: ProgressBar,
        recyclerView: RecyclerView,
        type:String,
        page:Int
    ) {
        viewModel.getMovies(type, page)
        val resultValue = when(type) {
            Config.NOW_PLAYING_MOVIES -> viewModel.resultValueNowPlaying
            Config.POPULAR_MOVIES -> viewModel.resultValuePopuler
            Config.TOP_RATED_MOVIES -> viewModel.resultValueTopRated
            else -> viewModel.resultValueUpcoming
        }

        resultValue?.observe(livecycleOwner){result ->
            when (result) {
                is Resource.Loading -> {
                    updateUI(true, progressBar)
                }
                is Resource.Success -> {
                    updateUI(false, progressBar)
                    if (page > 1) {
                        result.data?.forEach {
                            movieAdapter.addItem(it)
                        }
                    } else {
                        movieAdapter.setData(result.data)
                    }
                }
                is Resource.Error -> {
                    updateUI(false,progressBar)
                }
                else -> {
                    updateUI(false, progressBar)
                }
            }
        }

        movieAdapter.onItemClick = { movie ->
            val dialog =  detailMoviewDialog(
                movie = movie,
                viewModel = viewModel,
                livecycleOwner = livecycleOwner,
                activity = activity,
                reviewMovieAdapter = reviewMovieAdapter
            )
            dialog.show()
        }

        if (page < 2) {
            with(recyclerView) {
                layoutManager = GridLayoutManager(App.context, 2)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    fun detailMoviewDialog(movie:MovieModel,viewModel: MovieViewModel,livecycleOwner: LifecycleOwner,activity: Activity, reviewMovieAdapter: ReviewMovieAdapter): AlertDialog {
        val mBuild = AlertDialog.Builder(activity)
        val dialogView = activity.layoutInflater.inflate(R.layout.layout_detail_movie, null)
        val binding = LayoutDetailMovieBinding.bind(dialogView)
        mBuild.setView(dialogView)
        val dialog = mBuild.create()

        val btnClose = binding.btnClose
        val videoWeb = binding.webView
        val progressBar = binding.progressBar
        val desc = binding.tvDesc
        val info = binding.tvInfo
        val title = binding.tvTitle
        val rvReview = binding.rvReview
        val progressBarReview = binding.progressBarReview

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        val listGenreId = toList(movie.genreIds)
        val listGenre = ArrayList<String>()
        if (listGenreId != null) {
            viewModel.getGenreMovieById(listGenreId)
            viewModel.resultGenreMovie?.observe(livecycleOwner)
            {
                it.data?.map { listGenre.add(it.name.toString()) }
                info.text = activity.getString(R.string.movie_info, movie.release_date, listGenre.joinToString(", "), movie.vote_average, movie.original_language, movie.popularity)
            }
        }
        title.text = movie.title
        desc.text = movie.overview
        viewModel.getVideosMovieByMovieId(movie.id)
        viewModel.resultVideosMovie?.observe(livecycleOwner){result ->
            when (result) {
                is Resource.Loading -> updateUI(true,progressBar)
                is Resource.Success -> {
                    updateUI(false,progressBar)
                    val videoMovie = result.data?.firstOrNull{ it.site == "YouTube" && it.type == "Trailer"}
                    if (videoMovie != null) {
                        val url = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/${videoMovie.key}\" frameborder=\"0\" allowfullscreen></iframe>"
                        videoWeb.loadData(url,  "text/html", "utf-8")
                        videoWeb.settings.javaScriptEnabled = true
                        videoWeb.webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                updateUI(false,progressBar)
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    updateUI(false,progressBar)
                }
                else -> {
                    updateUI(false,progressBar)
                }
            }
        }

        viewModel.getReviewsMovieByMovieId(movie.id)
        viewModel.resultReviewsMovie?.observe(livecycleOwner){result ->
            when (result) {
                is Resource.Loading -> updateUI(true, progressBarReview)
                is Resource.Success -> {
                    updateUI(false, progressBarReview)
                    reviewMovieAdapter.setData(result.data)

                }
                is Resource.Error -> {
                    updateUI(false, progressBarReview)
                }
                else -> {
                    updateUI(false, progressBarReview)
                }
            }
        }
        with(rvReview) {
            layoutManager =  GridLayoutManager(activity,2,GridLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = reviewMovieAdapter
        }

        return dialog
    }

}