package com.italiasimon.themoviedatabase.ui.movieDetail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.italiasimon.themoviedatabase.R
import com.italiasimon.themoviedatabase.databinding.FragmentMovieDetailBinding
import com.italiasimon.themoviedatabase.setDisplayHomeAsUpEnabled
import com.italiasimon.themoviedatabase.setTitle
import kotlinx.coroutines.runBlocking

class MovieDetailFragment : Fragment() {

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment using data binding
        val binding = FragmentMovieDetailBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // action bar options
        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            setTitle(viewModel.movie.title)

            viewModel.isFavorite.observe(viewLifecycleOwner) {
                it?.let { value ->
                    //TODO: Update toolbar favorites icon
                }
            }
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
                onFavoritesOptionsItemTapped()
            }

            // on up pressed
            android.R.id.home -> {
                requireActivity().onBackPressed()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun onFavoritesOptionsItemTapped() = runBlocking {
        viewModel.updateFavorites()
    }
}