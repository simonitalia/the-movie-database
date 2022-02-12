package com.italiasimon.themoviedatabase.ui.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.italiasimon.themoviedatabase.R
import com.italiasimon.themoviedatabase.databinding.FragmentMainBinding
import com.italiasimon.themoviedatabase.models.Movie
import com.italiasimon.themoviedatabase.ui.adapter.MovieAdapter
import com.italiasimon.themoviedatabase.ui.adapter.MovieRecyclerViewAdapterListener
import com.italiasimon.themoviedatabase.ui.base.BaseFragment

class MainFragment : BaseFragment(), MovieRecyclerViewAdapterListener {

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

        setTitle(getString(R.string.app_name_short))
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

        /*
            * Observe view model live data changes
         */

        // Popular movies
        viewModel.popularMovies.observe(viewLifecycleOwner) {
            it?.let { movies ->
                popularMoviesAdapter.submitList(movies)
            }
        }

        viewModel.showToastPopular.observe(viewLifecycleOwner) { showToast ->
            if (showToast) {
                Toast.makeText(
                    this.context,
                    getString(R.string.success_fetch_popular_movies),
                    Toast.LENGTH_SHORT
                ).show()

                viewModel.showToastCompleted(MainViewModel.MovieListCategory.POPULAR)
            }
        }

        viewModel.showErrorPopular.observe(viewLifecycleOwner) { showError ->
            if (showError) {
                val snack = Snackbar.make(
                    view,
                    getString(R.string.error_fetch_popular_movies),
                    Snackbar.LENGTH_INDEFINITE
                )
                snack.setAction(getString(R.string.snackbar_action_try_again)) {
                    viewModel.updateMovies((MainViewModel.MovieListCategory.ALL))
                }
                snack.show()

                viewModel.showErrorCompleted(MainViewModel.MovieListCategory.POPULAR)
            }
        }

        // Top rated movies
        viewModel.topRatedMovies.observe(viewLifecycleOwner) {
            it?.let { movies ->
                topRatedMoviesAdapter.submitList(movies)
            }
        }

        viewModel.showToastTopRated.observe(viewLifecycleOwner) { showToast ->
            if (showToast) {
                Toast.makeText(
                    this.context,
                    getString(R.string.success_fetch_top_rated_movies),
                    Toast.LENGTH_SHORT
                ).show()

                viewModel.showToastCompleted(MainViewModel.MovieListCategory.TOP_RATED)
            }
        }

        viewModel.showErrorTopRated.observe(viewLifecycleOwner) { showError ->
            if (showError) {
                val snack = Snackbar.make(
                    view,
                    getString(R.string.error_fetch_top_rated_movies),
                    Snackbar.LENGTH_INDEFINITE
                )
                snack.setAction(getString(R.string.snackbar_action_try_again)) {
                    viewModel.updateMovies((MainViewModel.MovieListCategory.ALL))
                }
                snack.show()

                viewModel.showErrorCompleted(MainViewModel.MovieListCategory.TOP_RATED)
            }
        }

        // trigger navigation on selected movie
        viewModel.selectedMovie.observe(viewLifecycleOwner) {
            it?.let { movie ->
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToMovieDetailFragment(movie))
                viewModel.showMovieDetailFragmentComplete()
            }
        }
    }

    // pass selected movie to view model
    override fun onMovieItemPressed(movie: Movie) {
       viewModel.onSelectedMovie(movie)
    }
}