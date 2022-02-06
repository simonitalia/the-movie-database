package com.italiasimon.themoviedatabase.ui.favorites

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.italiasimon.themoviedatabase.ui.base.BaseViewModel

class FavoritesViewModel(
    app: Application,
) : BaseViewModel(app) {

    /*
     * Factory for constructing this ViewModel with constructor parameters
     */
    class Factory(val app: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FavoritesViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }

    companion object {
        private const val TAG = "FavoritesViewModel"
    }
}