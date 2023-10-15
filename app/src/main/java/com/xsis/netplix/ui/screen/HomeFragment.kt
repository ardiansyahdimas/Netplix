package com.xsis.netplix.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.xsis.core.data.Resource
import com.xsis.core.ui.CarouselAdapter
import com.xsis.core.ui.MovieAdapter
import com.xsis.core.ui.ReviewMovieAdapter
import com.xsis.core.utils.CarouselLayoutManager
import com.xsis.core.utils.Config
import com.xsis.core.utils.Utils.carouselToMovieModel
import com.xsis.core.utils.Utils.updateUI
import com.xsis.netplix.databinding.FragmentHomeBinding
import com.xsis.netplix.ui.MovieViewModel
import com.xsis.netplix.ui.screen.ShowUI.detailMoviewDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var snapHelper: SnapHelper? = null
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var carouselAdapter: CarouselAdapter
    private lateinit var nowPlayingAdapter: MovieAdapter
    private lateinit var populerAdapter: MovieAdapter
    private lateinit var topRatedAdapter: MovieAdapter
    private lateinit var upcomingAdapter: MovieAdapter
    private lateinit var reviewMovieAdapter: ReviewMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carouselAdapter = CarouselAdapter()
        nowPlayingAdapter = MovieAdapter()
        populerAdapter = MovieAdapter()
        topRatedAdapter = MovieAdapter()
        upcomingAdapter = MovieAdapter()
        reviewMovieAdapter = ReviewMovieAdapter()
        getMovies(Config.NOW_PLAYING_MOVIES)
        getMovies(Config.POPULAR_MOVIES)
        getMovies(Config.TOP_RATED_MOVIES)
        getMovies(Config.UPCOMING_MOVIES)
        viewModel.getGenresMovie.observe(requireActivity()){}
        getCarouselsMovie()
    }

    private fun getMovies(type:String) {
        viewModel.getMovies(type, 1)
        val resultValue = when(type) {
            Config.NOW_PLAYING_MOVIES -> viewModel.resultValueNowPlaying
            Config.POPULAR_MOVIES -> viewModel.resultValuePopuler
            Config.TOP_RATED_MOVIES -> viewModel.resultValueTopRated
            else -> viewModel.resultValueUpcoming
        }


        val movieAdapter = when (type){
            Config.NOW_PLAYING_MOVIES -> nowPlayingAdapter
            Config.POPULAR_MOVIES -> populerAdapter
            Config.TOP_RATED_MOVIES -> topRatedAdapter
            else -> upcomingAdapter
        }

        val recylerview = when (type){
            Config.NOW_PLAYING_MOVIES -> binding.rvNowPlaying
            Config.POPULAR_MOVIES -> binding.rvPopuler
            Config.TOP_RATED_MOVIES -> binding.rvTopRated
            else -> binding.rvUpcoming
        }

        val progressBar = when (type){
            Config.NOW_PLAYING_MOVIES -> binding.progressBar
            Config.POPULAR_MOVIES -> binding.progressBar1
            Config.TOP_RATED_MOVIES -> binding.progressBar2
            else -> binding.progressBar3
        }

        resultValue?.observe(requireActivity()){result ->
            when (result) {
                is Resource.Loading -> updateUI(true, progressBar)
                is Resource.Success -> {
                    updateUI(false, progressBar)
                    movieAdapter.setData(result.data)
                }
                is Resource.Error -> {
                    updateUI(false, progressBar)
                }
                else -> {
                    updateUI(false, progressBar)
                }
            }
        }

        movieAdapter.onItemClick =  {movie ->
            val dialog =  detailMoviewDialog(
                movie = movie,
                viewModel = viewModel,
                livecycleOwner = requireActivity(),
                activity = requireActivity(),
                reviewMovieAdapter = reviewMovieAdapter
            )
            dialog.show()
        }

        with(recylerview) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun getCarouselsMovie(){
        val progressBar = binding.carouselProgressBar
        val carouselView = binding.rvCarousel
        viewModel.getCarouselsMovie.observe(requireActivity()){ result ->
            when (result) {
                is Resource.Loading -> updateUI(true, progressBar)
                is Resource.Success -> {
                    updateUI(false, progressBar)
                    carouselAdapter.setData(result.data)
                }
                is Resource.Error -> {
                    updateUI(false, progressBar)
                }
                else -> {
                    updateUI(false, progressBar)
                }
            }
        }

        carouselAdapter.onItemClick = { carousel ->
            val movie = carouselToMovieModel(carousel)
            val dialog =  detailMoviewDialog(
                movie = movie,
                viewModel = viewModel,
                livecycleOwner = requireActivity(),
                activity = requireActivity(),
                reviewMovieAdapter = reviewMovieAdapter
            )
            dialog.show()
        }

        with(carouselView) {
            layoutManager = CarouselLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = carouselAdapter
        }

        snapHelper = PagerSnapHelper()
        snapHelper?.attachToRecyclerView(carouselView)
        binding.indicator.attachToRecyclerView(carouselView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}