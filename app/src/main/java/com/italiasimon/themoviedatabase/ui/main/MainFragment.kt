package com.italiasimon.themoviedatabase.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
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
    private lateinit var adapter: MoviesAdapter
    private lateinit var listener: MovieRecyclerViewAdapterListener

    // inflate layout using Data Binding, and bind fragment with this ui controller
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )

        // observer and bind viewModel with fragment
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Recycler Adapter Item View listener
        listener = this
        adapter = MoviesAdapter(listener, viewModel.movies.value ?: emptyList())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // observe view model live data changes

        // movies
        viewModel.movies.observe(viewLifecycleOwner, {
            Snackbar.make(
                view,
                getString(R.string.success_fetch_movies),
                Snackbar.LENGTH_SHORT
            ).show()

            // update list
            adapter.onMoviesUpdated(it)
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
}

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 * Interface for communication between adapter and fragment classes
 */
interface MovieRecyclerViewAdapterListener {
    fun onItemViewPressed(movie: Movie)
}

class MoviesAdapter(
    private val listener: MovieRecyclerViewAdapterListener,
    private var movies: List<Movie>
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size
    override fun getItemId(position: Int): Long = super.getItemId(position)
    override fun getItemViewType(position: Int): Int = super.getItemViewType(position)


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = movies[position]
        holder.bind(movieItem)

        // movie item press event listener
        listener.onItemViewPressed(movieItem)
    }

    fun onMoviesUpdated(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)
        }
    }
}