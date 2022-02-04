package com.italiasimon.themoviedatabase.ui.movieDetail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.italiasimon.themoviedatabase.R
import com.italiasimon.themoviedatabase.databinding.FragmentMovieDetailBinding
import com.italiasimon.themoviedatabase.setDisplayHomeAsUpEnabled
import com.italiasimon.themoviedatabase.setTitle
import com.italiasimon.themoviedatabase.ui.base.BaseFragment
import kotlinx.coroutines.runBlocking

class MovieDetailFragment : BaseFragment() {

    companion object {
        private const val TAG = "MovieDetailFragment"
    }

    // lazily initialize ViewModel using .Factory to pass in parameters
    private val viewModel: MovieDetailViewModel by lazy {
        val activity = requireNotNull(this.activity)  {
            "You can only access the viewModel after onViewCreated()"
        }

        val movie = MovieDetailFragmentArgs.fromBundle(requireArguments()).movie
            // data passed into fragment via safe args on navigation

        ViewModelProvider(this, MovieDetailViewModel.Factory(activity.application, movie)).get(MovieDetailViewModel::class.java)
    }

    private lateinit var binding: FragmentMovieDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment using data binding
        binding = FragmentMovieDetailBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.toolbarMovieDetailTop.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                // on favorite pressed
                R.id.action_favorite -> {
                    onFavoritesOptionsItemTapped()
                    true
                }

                // on up pressed
                android.R.id.home -> {
                    requireActivity().onBackPressed()
                    true
                }

                else -> false
            }
        }

        binding.toolbarMovieDetailTop.title = viewModel.movie.title
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            val menu = binding.toolbarMovieDetailTop.menu

            menu.findItem(R.id.action_favorite).icon = if (isFavorite) {
                this.activity?.getDrawable(R.drawable.ic_heart_fill_24dp)
            } else {
                this.activity?.getDrawable(R.drawable.ic_heart_outline_24dp)
            }
        }

        viewModel.showToast.observe(viewLifecycleOwner) { showToast ->
            if (showToast) {
                onMovieFavoriteUpdated()
            }
        }

        viewModel.showSnackbarError.observe(viewLifecycleOwner) { showError ->
            if (showError) {
                onMovieSaveOrRemoveFavoriteError(view)
            }
        }
    }

    private fun onFavoritesOptionsItemTapped() = runBlocking {
        viewModel.updateFavorites()
    }

    private fun onMovieFavoriteUpdated() {

        //show toast
        val message = if (viewModel.isFavorite.value == true) {
            getString(R.string.movie_added_to_favorites)
        } else {
            getString(R.string.movie_removed_from_favorites)
        }

        Toast.makeText(
            this.context,
            message,
            Toast.LENGTH_SHORT
        ).show()

        viewModel.showToastCompleted()
    }

    private fun onMovieSaveOrRemoveFavoriteError(view: View) {

        //show snackbar error with message and action
        val message = if (viewModel.isFavorite.value == true) {
            getString(R.string.error_removing_movie_from_favorites)
        } else {
            getString(R.string.error_adding_movie_to_favorites)
        }

        val snack = Snackbar.make(
            view,
            message,
            Snackbar.LENGTH_LONG
        )
        snack.setAction(getString(R.string.snackbar_action_try_again)) {
            onFavoritesOptionsItemTapped()
        }
        snack.show()

        //reset showSnackbarError value
        viewModel.showSnackBarErrorCompleted()
    }
}