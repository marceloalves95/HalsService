package br.com.halsservice.framework.viewmodel.servico

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by RubioAlves on 13/04/2021
 */
class ServicoViewModelFactory (val application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = ServicoViewModel(application) as T


}