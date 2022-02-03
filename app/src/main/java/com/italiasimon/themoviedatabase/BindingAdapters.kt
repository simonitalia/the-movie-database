package com.italiasimon.themoviedatabase

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.italiasimon.themoviedatabase.tmdbClient.TmdbApi

/**
 * Multi Fragment bindings.
 */

@BindingAdapter("bindText")
fun bindTextView(textView: TextView, text: String) {
    textView.text = text
    textView.contentDescription = text
}

@BindingAdapter("bindPosterImagePath")
fun bindPosterImage(imageView: ImageView, imagePath: String) {
    Glide.with(imageView)
        .load(Constants.PosterSize.getImagePath(Constants.PosterSize.W_342, imagePath))
        .transform(CenterCrop())
        .into(imageView)
}

/**
 * fragment_main bindings.
 */

@BindingAdapter("bindApiStatusArg1", "bindApiStatusArg2", requireAll = true)
fun bindApiStatus(progressBarView: ProgressBar, apiStatusArg1: TmdbApi.ApiStatus, apiStatusArg2: TmdbApi.ApiStatus) {
    progressBarView.visibility = if (apiStatusArg1 == TmdbApi.ApiStatus.LOADING || apiStatusArg2 == TmdbApi.ApiStatus.LOADING) View.VISIBLE else View.GONE
}

/**
 * fragment_movie_detail bindings.
 */

@BindingAdapter("bindBackdropImagePath")
fun bindBackdropImage(imageView: ImageView, imagePath: String) {
    Glide.with(imageView)
        .load(Constants.BackdropSize.getImagePath(Constants.BackdropSize.W_1280, imagePath))
        .transform(CenterCrop())
        .into(imageView)
}

@BindingAdapter("bindRating")
fun bindRating(ratingBar: RatingBar, rating: Float) {
    ratingBar.rating = rating / 2
}


