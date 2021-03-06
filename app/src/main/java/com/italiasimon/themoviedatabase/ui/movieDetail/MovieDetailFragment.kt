package com.italiasimon.themoviedatabase.ui.movieDetail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.italiasimon.themoviedatabase.R
import com.italiasimon.themoviedatabase.databinding.FragmentMovieDetailBinding
import com.italiasimon.themoviedatabase.ui.base.BaseFragment
import kotlinx.coroutines.launch
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

        // bind addRemoveFavoriteFab using setOnTouchListener to
        // intercept motion layout events
        binding.addRemoveFavoriteFab.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                onAddOrRemoveFavoritesPressed()
                true
            }
            false
        }

        viewModel.viewModelScope.launch {
            viewModel.getMovieById()
        }

        setTitle(viewModel.movie.title)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
            * Observe view model live data changes
         */

        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            setFavoritesImage(isFavorite)
        }

        viewModel.showToast.observe(viewLifecycleOwner) { showToast ->
            if (showToast) {
                showToast()
            }
        }

        viewModel.showSnackbarError.observe(viewLifecycleOwner) { showError ->
            if (showError) {
                onMovieSaveOrRemoveFavoriteError(view)
            }
        }

        viewModel.navigationCommand.observe(viewLifecycleOwner) { showFavoritesFragment ->
            if (showFavoritesFragment) {
                findNavController().navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToFavoritesFragment())
                viewModel.onShowFavoritesFragmentCompleted()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.favorites_menu, menu)

        // set favorite action icon
        menu.findItem(R.id.action_add_remove_favorite).icon = if (viewModel.isFavorite.value == true) {
            requireActivity().getDrawable(R.drawable.ic_heart_fill_white_24dp)
        } else {
            requireActivity().getDrawable(R.drawable.ic_heart_outline_white_24dp)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            // on favorite pressed
            R.id.action_add_remove_favorite -> {
                onAddOrRemoveFavoritesPressed()
                true
            }

            // on favorites overflow menu pressed
            R.id.action_navigate_to_favorites_fragment -> {
                viewModel.showFavoritesFragment()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onAddOrRemoveFavoritesPressed() = runBlocking {
        viewModel.updateFavorites()
    }

    private fun setFavoritesImage(isFavorite: Boolean?) {

        //recreate options menu to refresh favorites toolbar icon
        requireActivity().invalidateOptionsMenu()

        //set fab icon
        if (isFavorite == true) {
            binding.addRemoveFavoriteFab.setImageDrawable(context?.getDrawable(R.drawable.ic_heart_fill_white_24dp))

        } else {
            binding.addRemoveFavoriteFab.setImageDrawable(context?.getDrawable(R.drawable.ic_heart_outline_white_24dp))
        }
    }

    private fun showToast() {

        val message = if (viewModel.isFavorite.value == true) {
            getString(R.string.added_to_favorites)

        } else {
            getString(R.string.removed_from_favorites)
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
            onAddOrRemoveFavoritesPressed()
        }
        snack.show()

        //reset showSnackbarError value
        viewModel.showSnackbarErrorCompleted()
    }
}