package com.italiasimon.themoviedatabase.ui.main

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.italiasimon.themoviedatabase.R
import com.italiasimon.themoviedatabase.databinding.FragmentMainBinding
import com.italiasimon.themoviedatabase.models.Movie
import com.italiasimon.themoviedatabase.setDisplayHomeAsUpEnabled
import com.italiasimon.themoviedatabase.setTitle
import com.italiasimon.themoviedatabase.ui.adapter.MovieAdapter
import com.italiasimon.themoviedatabase.ui.adapter.MovieRecyclerViewAdapterListener

class MainFragment: Fragment(), MovieRecyclerViewAdapterListener {

    // lazily initialize ViewModel using .Factory to pass in parameters
    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity)  {
            "You can only access the viewModel after onViewCreated()"
        }

        ViewModelProvider(this, MainViewModel.Factory(activity.application)).get(MainViewModel::class.java)
    }

    // Adapter properties
    private lateinit var popularMoviesAdapter: MovieAdapter
    private lateinit var topRatedMoviesAdapter: MovieAdapter
    private lateinit var listener: MovieRecyclerViewAdapterListener

    // inflate layout using Data Binding, and bind fragment with this ui controller
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        // inflate layout using Data Binding, and bind fragment with this ui controller
        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )

        // Recycler Adapter Item View listener
        listener = this
        popularMoviesAdapter = MovieAdapter(listener)
        topRatedMoviesAdapter = MovieAdapter(listener)

        // bindings
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.popularMoviesRecyclerView.adapter = this.popularMoviesAdapter
        binding.topRatedMoviesRecyclerView.adapter = this.topRatedMoviesAdapter

        setHasOptionsMenu(true)
        return binding.root
    }

    /*
     * Called immediately after onCreateView() has returned, and fragment's
     * view hierarchy has been created.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // observe view model live data changes

        // popular movies
        viewModel.popularMovies.observe(viewLifecycleOwner) {
            it?.let { movies ->
                Snackbar.make(
                    view,
                    getString(R.string.movies_updated),
                    Snackbar.LENGTH_SHORT
                ).show()

                // update list
                onMoviesUpdated(movies, MainViewModel.MovieListCategory.POPULAR)
            }
        }

        // showError, popular movies
        viewModel.showErrorPopular.observe(viewLifecycleOwner) { showError ->
            if (showError) {
                val snack = Snackbar.make(
                    view,
                    getString(R.string.error_fetch_popular_movies),
                    Snackbar.LENGTH_INDEFINITE
                )
                snack.setAction(getString(R.string.snackbar_action_try_again)) {
                    viewModel.getMovies(MainViewModel.MovieListCategory.ALL)
                }
                snack.show()
            }
        }

        // popular movies
        viewModel.topRatedMovies.observe(viewLifecycleOwner) {
            it?.let { movies ->
                Snackbar.make(
                    view,
                    getString(R.string.movies_updated),
                    Snackbar.LENGTH_SHORT
                ).show()

                // update list
                onMoviesUpdated(movies, MainViewModel.MovieListCategory.TOP_RATED)
            }
        }

        // showError, top rated movies
        viewModel.showErrorTopRated.observe(viewLifecycleOwner) { showError ->
            if (showError) {
                val snack = Snackbar.make(
                    view,
                    getString(R.string.error_fetch_top_rated_movies),
                    Snackbar.LENGTH_INDEFINITE
                )
                snack.setAction(getString(R.string.snackbar_action_try_again)) {
                    viewModel.getMovies(MainViewModel.MovieListCategory.ALL)
                }
                snack.show()
            }
        }

        // trigger nagigatin on selected movie
        viewModel.selectedMovie.observe(viewLifecycleOwner) {
            it?.let { movie ->
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToMovieDetailFragment(movie))
                viewModel.showMovieDetailFragmentComplete()
            }
        }

        setDisplayHomeAsUpEnabled(false)
        setTitle(getString(R.string.app_name_short))
    }

    override fun onResume() {
        super.onResume()
    }

    // pass selected movie to view model
    override fun onItemViewPressed(movie: Movie) {
       viewModel.onSelectedMovie(movie)
    }

    /*
     * Options Menu setup
     */

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId)  {
        R.id.menu_item_sort_popular_a_z -> {
            viewModel.sortMovies(true, MainViewModel.MovieListCategory.POPULAR)
            true
        }

        R.id.menu_item_sort_popular_z_a -> {
            viewModel.sortMovies(false, MainViewModel.MovieListCategory.POPULAR)
            true
        }

        else ->  super.onOptionsItemSelected(item)
    }

    private fun onMoviesUpdated(movies: List<Movie>, category: MainViewModel.MovieListCategory) {

        when (category) {
            MainViewModel.MovieListCategory.POPULAR -> popularMoviesAdapter.submitList(movies)

            MainViewModel.MovieListCategory.TOP_RATED -> topRatedMoviesAdapter.submitList(movies)
            else -> return
        }
    }
}