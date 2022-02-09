package com.italiasimon.themoviedatabase.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.italiasimon.themoviedatabase.R
import com.italiasimon.themoviedatabase.databinding.FragmentFavoritesBinding
import com.italiasimon.themoviedatabase.models.Movie
import com.italiasimon.themoviedatabase.ui.adapter.FavoriteMovieAdapter
import com.italiasimon.themoviedatabase.ui.adapter.MovieAdapter
import com.italiasimon.themoviedatabase.ui.adapter.MovieRecyclerViewAdapterListener
import com.italiasimon.themoviedatabase.ui.base.BaseFragment

class FavoritesFragment : BaseFragment(), MovieRecyclerViewAdapterListener  {

    companion object {
        private const val TAG = "FavoritesFragment"
    }

    // lazily initialize ViewModel using .Factory to pass in parameters
    private val viewModel: FavoritesViewModel by lazy {
        val activity = requireNotNull(this.activity)  {
            "You can only access the viewModel after onViewCreated()"
        }

        ViewModelProvider(this, FavoritesViewModel.Factory(activity.application)).get(
            FavoritesViewModel::class.java)
    }

    private lateinit var adapter: FavoriteMovieAdapter
    private lateinit var listener: MovieRecyclerViewAdapterListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // inflate layout using Data Binding, and bind fragment with this ui controller
        val binding: FragmentFavoritesBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favorites,
            container,
            false
        )

        listener = this
        adapter = FavoriteMovieAdapter(listener)

        // bindings
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.favoriteMoviesRecyclerView.adapter = this.adapter

        setTitle(getString(R.string.fragment_favorites_title))
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.favoriteMovies.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
        }

        viewModel.showToast.observe(viewLifecycleOwner) { showToast ->
            if (showToast) {
                if (showToast) {
                    Toast.makeText(
                        this.context,
                        "${viewModel.favoriteMovies.value?.size} favorite movies",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.showToastCompleted()
                }
            }
        }

        viewModel.showSnackbarError.observe(viewLifecycleOwner) { showError ->
            if (showError) {
                Snackbar.make(
                    view,
                    getString(R.string.error_fetch_favorite_movies),
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(getString(R.string.snackbar_action_try_again)) {
                    viewModel.getFavorites()
                }.show()

                viewModel.showSnackbarErrorCompleted()
            }
        }
    }

    override fun onItemViewPressed(movie: Movie) {
        // TODO: "Not yet implemented"
    }

}