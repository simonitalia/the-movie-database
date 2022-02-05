package com.italiasimon.themoviedatabase.ui.base

import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    /*
        * Toolbar Menu Items
     */

    protected open fun onNavigationBackPressed() {
        requireActivity().onBackPressed()
    }

    protected open fun onToolbarNavigationItemSelected(view: View) {}
    protected open fun onToolbarMenuItemSelected(menuItem: MenuItem) {}
}