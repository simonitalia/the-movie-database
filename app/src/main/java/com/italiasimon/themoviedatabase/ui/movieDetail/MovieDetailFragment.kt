package com.italiasimon.themoviedatabase.ui.movieDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import com.italiasimon.themoviedatabase.databinding.FragmentMovieDetailBinding
import com.italiasimon.themoviedatabase.setDisplayHomeAsUpEnabled
import com.italiasimon.themoviedatabase.setTitle

class MovieDetailFragment : Fragment() {

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

        // support back button
        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // on back button pressed
        if (item.itemId == android.R.id.home) {
            requireActivity().onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}