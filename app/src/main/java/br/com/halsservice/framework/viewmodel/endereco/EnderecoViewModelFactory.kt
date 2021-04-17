package br.com.halsservice.framework.viewmodel.endereco

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.halsservice.data.repository.EnderecoRepository
import br.com.halsservice.framework.api.ApiHelper

/**
 * Created by RubioAlves on 16/04/2021
 */
class EnderecoViewModelFactory (private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EnderecoViewModel::class.java)) {
            return EnderecoViewModel(EnderecoRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}