package com.italiasimon.themoviedatabase.ui.base

import android.view.MenuItem
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    /*
        * Toolbar Menu Items
     */
    protected open fun onToolbarMenuItemSelected(menuItem: MenuItem) {}
}