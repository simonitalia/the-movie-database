package com.italiasimon.themoviedatabase.ui.movieDetail

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.italiasimon.themoviedatabase.R
import com.italiasimon.themoviedatabase.databinding.FragmentMovieDetailBinding
import com.italiasimon.themoviedatabase.setDisplayHomeAsUpEnabled
import com.italiasimon.themoviedatabase.setTitle

class MovieDetailFragment : Fragment() {

    companion object {
        private const val TAG = "MovieDetailFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // properties to pass into viewModel initialization
        val application = requireNotNull(activity).application
        val movie = MovieDetailFragmentArgs.fromBundle(requireArguments()).movie
            // data passed into fragment via safe args on navigation

        // initialize ViewModel Factory and create view model
        val viewModelFactory = MovieDetailViewModel.Factory(application, movie)

        // Inflate the layout for this fragment using data binding
        val binding = FragmentMovieDetailBinding.inflate(inflater)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(MovieDetailViewModel::class.java)

        binding.lifecycleOwner = this

        setTitle(movie.title)

        // action bar options
        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.movie_detail_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            // on favorite pressed
            R.id.menu_item_favorite -> {
                //TODO: Implement save to favorites
                Log.i(TAG, ".onOptionsItemSelected: Favorite button tapped")
            }

            // on up pressed
            android.R.id.home -> {
                requireActivity().onBackPressed()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}