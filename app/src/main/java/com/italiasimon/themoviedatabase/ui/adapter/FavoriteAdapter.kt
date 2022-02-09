package com.italiasimon.themoviedatabase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.italiasimon.themoviedatabase.R
import com.italiasimon.themoviedatabase.databinding.ItemFavoriteBinding
import com.italiasimon.themoviedatabase.models.Movie

class FavoriteMovieAdapter(
    private val listener: MovieRecyclerViewAdapterListener,
) : ListAdapter<Movie, FavoriteMovieAdapter.ViewHolder>(AdapterDiffCallback()) {

    /*
     * Diff Util for managing Data changes
     */
    class AdapterDiffCallback: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }

    /*
     * Bridge between Adapter class and xml ui Views
     * Setting Views with data handled by BindingAdapters
     */
    class ViewHolder(val viewDataBinding: ItemFavoriteBinding): RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.item_favorite
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val withDataBinding: ItemFavoriteBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ViewHolder.LAYOUT,
            parent,
            false)
        return ViewHolder(withDataBinding)
    }

    // bridge between data item and item view (ui layout)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.viewDataBinding.also {
            it.favorite = item // connects to layout item data variable
        }

        // pass favorite item pressed back to listener
        holder.itemView.setOnClickListener {
            listener.onItemViewPressed(item)
        }
    }
}