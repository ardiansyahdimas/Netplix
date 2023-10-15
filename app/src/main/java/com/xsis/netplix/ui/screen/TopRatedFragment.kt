package com.xsis.netplix.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.core.ui.MovieListAdapter
import com.xsis.core.ui.ReviewMovieAdapter
import com.xsis.core.utils.Config
import com.xsis.netplix.databinding.FragmentTopRatedBinding
import com.xsis.netplix.ui.MovieViewModel
import com.xsis.netplix.ui.screen.ShowUI.getMovies
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedFragment : Fragment() {
    private var _binding: FragmentTopRatedBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieListAdapter
    private lateinit var reviewMovieAdapter: ReviewMovieAdapter
    private val viewModel: MovieViewModel by viewModels()
    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopRatedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAdapter = MovieListAdapter()
        reviewMovieAdapter = ReviewMovieAdapter()
        getMovies(
            activity = requireActivity(),
            viewModel = viewModel,
            livecycleOwner =  requireActivity(),
            movieAdapter = movieAdapter,
            reviewMovieAdapter = reviewMovieAdapter,
            progressBar = binding.progressBar,
            recyclerView = binding.rvMovie,
            type = Config.TOP_RATED_MOVIES,
            page = currentPage
        )

        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.rvMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                    currentPage++
                    getMovies(
                        activity = requireActivity(),
                        viewModel = viewModel,
                        livecycleOwner =  requireActivity(),
                        movieAdapter = movieAdapter,
                        reviewMovieAdapter = reviewMovieAdapter,
                        progressBar = binding.progressBar,
                        recyclerView = binding.rvMovie,
                        type = Config.TOP_RATED_MOVIES,
                        page = currentPage
                    )
                }
            }
        })
    }
}