package com.fitlog.app.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * A tiny generic [ViewModelProvider.Factory] so each ViewModel can take its
 * repository dependencies through its constructor (and therefore be unit
 * tested in isolation) without pulling in a full DI framework.
 */
class ViewModelFactory<T : ViewModel>(private val creator: () -> T) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <VM : ViewModel> create(modelClass: Class<VM>): VM = creator() as VM
}
