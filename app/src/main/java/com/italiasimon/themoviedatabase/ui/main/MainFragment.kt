package com.italiasimon.themoviedatabase.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.italiasimon.themoviedatabase.R
import com.italiasimon.themoviedatabase.databinding.FragmentMainBinding

class MainFragment: Fragment() {

    // lazily initialize MainViewModel using .Factory to pass in application parameter
    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity)  {
            "You can only access the viewModel after onViewCreated()"
        }

        ViewModelProvider(this, MainViewModel.Factory(activity.application)).get(MainViewModel::class.java)
    }

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
}