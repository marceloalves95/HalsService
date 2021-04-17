package br.com.halsservice.framework.viewmodel.cliente

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by RubioAlves on 13/04/2021
 */
class ClienteViewModelFactory(val application: Application) :ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = ClienteViewModel(application) as T


}