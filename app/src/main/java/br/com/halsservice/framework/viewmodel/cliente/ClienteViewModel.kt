package br.com.halsservice.framework.viewmodel.cliente

import android.app.Application
import androidx.lifecycle.*
import br.com.halsservice.data.repository.ClienteRepository
import br.com.halsservice.domain.Cliente
import br.com.halsservice.domain.ClienteAndServico
import kotlinx.coroutines.launch

class ClienteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ClienteRepository(application)
    val listAll = MutableLiveData<MutableList<Cliente>>()
    private val listAllTransaction = MutableLiveData<MutableList<ClienteAndServico>>()


    fun adicionarCliente(cliente: Cliente){

        viewModelScope.launch {
            repository.insert(cliente)

            atualizarListaCliente()
            atualizarListaCompleta()

        }

    }
    fun atualizarListaCliente(){

        viewModelScope.launch {
            listAll.value = repository.getAll()
        }

    }
    fun atualizarListaCompleta(){

        viewModelScope.launch {
            listAllTransaction.value = repository.getClienteAndServico()
        }

    }

    fun deletarTodasReceitas(id:MutableList<Long>){

        viewModelScope.launch {

            repository.deleteAll(id)

        }


    }









}