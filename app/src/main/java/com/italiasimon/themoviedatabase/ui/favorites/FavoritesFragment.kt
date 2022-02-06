package com.italiasimon.themoviedatabase.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.italiasimon.themoviedatabase.R
import com.italiasimon.themoviedatabase.databinding.FragmentFavoritesBinding
import com.italiasimon.themoviedatabase.ui.base.BaseFragment

class FavoritesFragment : BaseFragment() {

    companion object {
        private const val TAG = "FavoritesFragment"
    }

    // lazily initialize ViewModel using .Factory to pass in parameters
    private val viewModel: FavoritesViewModel by lazy {
        val activity = requireNotNull(this.activity)  {
            "You can only access the viewModel after onViewCreated()"
        }

        ViewModelProvider(this, FavoritesViewModel.Factory(activity.application)).get(
            FavoritesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // inflate layout using Data Binding, and bind fragment with this ui controller
        val binding: FragmentFavoritesBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favorites,
            container,
            false
        )

        setTitle(getString(R.string.fragment_favorites_title))
        return binding.root
    }
}