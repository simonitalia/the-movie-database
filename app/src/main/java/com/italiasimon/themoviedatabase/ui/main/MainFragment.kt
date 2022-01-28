package com.italiasimon.themoviedatabase.ui.main

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.italiasimon.themoviedatabase.R
import com.italiasimon.themoviedatabase.databinding.FragmentMainBinding
import com.italiasimon.themoviedatabase.models.Movie

class MainFragment: Fragment(), MovieRecyclerViewAdapterListener {

    // lazily initialize MainViewModel using .Factory to pass in application parameter
    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity)  {
            "You can only access the viewModel after onViewCreated()"
        }

        ViewModelProvider(this, MainViewModel.Factory(activity.application)).get(MainViewModel::class.java)
    }

    // Adapter properties
    private lateinit var adapter: MovieAdapter
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
        adapter = MovieAdapter(listener)

        // bindings
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.movieRecyclerView.adapter = this.adapter

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

        // movies
        viewModel.movies.observe(viewLifecycleOwner, {
            it?.let { movies ->
                Snackbar.make(
                    view,
                    getString(R.string.success_fetch_movies),
                    Snackbar.LENGTH_SHORT
                ).show()

                // update list
                onMoviesUpdated(movies)
            }
        })

        // showError
        viewModel.showError.observe(viewLifecycleOwner, { showError ->
            if (showError) {
                val snack = Snackbar.make(
                    view,
                    getString(R.string.error_fetch_movies),
                    Snackbar.LENGTH_INDEFINITE
                )
                snack.setAction(getString(R.string.snackbar_action_try_again)) {
                    viewModel.getPopularMovies()
                }
                snack.show()
            }
        })
    }

    override fun onItemViewPressed(movie: Movie) {
        TODO("Not yet implemented")
        // navigate to item detail
    }

    /*
     * Options Menu setup
     */

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId)  {
        R.id.sort_a_z -> {
            viewModel.sortMovies(true)
            true
        }

        R.id.sort_z_a -> {
            viewModel.sortMovies(false)
            true
        }

        else ->  super.onOptionsItemSelected(item)
    }

    private fun onMoviesUpdated(movies: List<Movie>) {
        adapter.submitList(movies)
    }
}