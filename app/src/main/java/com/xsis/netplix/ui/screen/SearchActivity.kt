package com.xsis.netplix.ui.screen

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.xsis.core.R
import com.xsis.core.data.Resource
import com.xsis.core.ui.MovieListAdapter
import com.xsis.core.ui.ReviewMovieAdapter
import com.xsis.core.utils.Utils
import com.xsis.netplix.databinding.ActivitySearchBinding
import com.xsis.netplix.ui.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySearchBinding
    private lateinit var movieAdapter:MovieListAdapter
    private lateinit var reviewMovieAdapter: ReviewMovieAdapter
    private val viewModel: MovieViewModel by viewModels()
    private var queryValue = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        movieAdapter = MovieListAdapter()
        reviewMovieAdapter = ReviewMovieAdapter()
        menu()
        searchMovie(queryValue)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private  fun menu() {
        val menuHost: MenuHost = this
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menu.clear()
                menuInflater.inflate(com.xsis.netplix.R.menu.menu_search, menu)
                val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
                val searchView = menu.findItem(com.xsis.netplix.R.id.action_search)?.actionView as SearchView
                searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
                searchView.queryHint = resources.getString(R.string.search_movie)
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        if (query.isNotEmpty()) {
                            queryValue = query
                            searchMovie(queryValue)
                        }else {
                            queryValue = ""
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        queryValue = newText
                        searchMovie(queryValue)
                        return false
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    else -> false
                }
            }
        }, this , Lifecycle.State.RESUMED)
    }

    private fun searchMovie(query:String){
        viewModel.searchMovie(query)
        viewModel.resultValueSearch?.observe(this){result ->
            when (result) {
                is Resource.Loading -> {
                    binding.tvEmptyData.isVisible = false
                    Utils.updateUI(true, binding.progressBar)
                }
                is Resource.Success -> {
                    Utils.updateUI(false, binding.progressBar)
                    movieAdapter.setData(result.data)
                    binding.tvEmptyData.isVisible = result.data?.isEmpty() == true
                    binding.tvEmptyData.text = if (query != "") getString(R.string.tidak_ada_data, query) else getString(R.string.search_movie)
                }
                is Resource.Error -> {
                    binding.tvEmptyData.isVisible = false
                    Utils.updateUI(false, binding.progressBar)
                }
                else -> {
                    binding.tvEmptyData.isVisible = false
                    Utils.updateUI(false, binding.progressBar)
                }
            }
        }

        movieAdapter.onItemClick = { movie ->
            val dialog = ShowUI.detailMoviewDialog(
                movie = movie,
                viewModel = viewModel,
                livecycleOwner = this,
                activity = this,
                reviewMovieAdapter = reviewMovieAdapter
            )
            dialog.show()
        }
        with(binding.rvMovie) {
            layoutManager = GridLayoutManager(com.xsis.netplix.App.context, 2)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }
}