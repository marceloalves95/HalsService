package br.com.halsservice.framework.viewmodel.endereco

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.halsservice.data.repository.EnderecoRepository
import br.com.halsservice.domain.CEP
import kotlinx.coroutines.launch

/**
 * Created by RubioAlves on 16/04/2021
 */
class EnderecoViewModel(private val repository: EnderecoRepository): ViewModel() {

    fun buscaEnderecoPelo(cep: String): LiveData<EnderecoRepository.Resultado<CEP?>> =
        repository.buscaEndereco(cep)




}