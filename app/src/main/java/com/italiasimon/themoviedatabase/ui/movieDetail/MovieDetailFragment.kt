package com.italiasimon.themoviedatabase.ui.movieDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.italiasimon.themoviedatabase.databinding.FragmentMovieDetailBinding

//private const val MOVIE = "movie"

///**
// * A simple [Fragment] subclass.
// * Use the [MovieDetailFragment.newInstance] factory method to
// * create an instance of this fragment.
// */

class MovieDetailFragment : Fragment() {
//    private late init var movie: Movie

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
////            movie = it.getBundle(MOVIE)
//        }
//    }

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

        return binding.root
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @movie movie Movie Parameter.
//         * @return A new instance of fragment MovieDetailsFragment.
//         */
//        @JvmStatic
//        fun newInstance(movie: Movie) =
//            MovieDetailFragment().apply {
//                arguments = Bundle().apply {
////                    putBundle(MOVIE, movie)
//                }
//            }
//    }
}