package com.italiasimon.themoviedatabase

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.italiasimon.themoviedatabase.ui.main.MainViewModel

/**
 * General purpose bindings.
 */

@BindingAdapter("bindText")
fun bindTextView(textView: TextView, text: String) {
    textView.text = text
    textView.contentDescription = text
}

/**
 * fragment_main bindings .
 */

@BindingAdapter("posterImage")
fun bindPosterImage(imageView: ImageView, posterPath: String) {
    Glide.with(imageView)
        .load(Constants.getPosterImagePath(Constants.PosterSize.W_342, posterPath))
        .transform(CenterCrop())
//        .into(imageView)
}

@BindingAdapter("tmdbApiStatus")
fun bindApiStatus(progressBarView: ProgressBar, apiStatus: MainViewModel.TmdbApiStatus) {
    progressBarView.visibility = if (apiStatus == MainViewModel.TmdbApiStatus.LOADING) View.VISIBLE else View.GONE
}