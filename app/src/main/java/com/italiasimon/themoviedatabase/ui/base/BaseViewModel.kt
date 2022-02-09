package com.italiasimon.themoviedatabase.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel(
    app: Application
) : AndroidViewModel(app) {

    protected val _showToast = MutableLiveData<Boolean>()
    val showToast: LiveData<Boolean>
        get() = _showToast

    protected val _showSnackbarError = MutableLiveData<Boolean>()
    val showSnackbarError: LiveData<Boolean>
        get() = _showSnackbarError

    protected val _navigationCommand = MutableLiveData<Boolean>()
    val navigationCommand: LiveData<Boolean>
        get() = _navigationCommand


    protected fun showToast() {
        _showToast.postValue(true)
    }

    fun showToastCompleted() {
        _showToast.value = false
    }

    protected fun showSnackBarError() {
        _showSnackbarError.postValue(true)
    }

    fun showSnackbarErrorCompleted() {
        _showSnackbarError.value = false
    }
}


