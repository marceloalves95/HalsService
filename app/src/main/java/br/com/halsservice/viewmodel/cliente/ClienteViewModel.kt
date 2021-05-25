package br.com.halsservice.viewmodel.cliente

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.halsservice.data.repository.ClienteRepository
import br.com.halsservice.domain.Cliente
import br.com.halsservice.domain.ClienteAndServico
import kotlinx.coroutines.launch

class ClienteViewModel(private val repository:ClienteRepository) : ViewModel() {

    val listAll = MutableLiveData<MutableList<Cliente>>()
    val ids = MutableLiveData<Long>()
    val nomes = MutableLiveData<MutableList<String>>()
    val nomesCliente = MutableLiveData<MutableList<String>>()


    fun adicionarCliente(cliente: Cliente){

        viewModelScope.launch {

            repository.insert(cliente)
            atualizarListaCliente()

        }


    }
    fun atualizarCliente(cliente: Cliente){

        viewModelScope.launch {
            repository.update(cliente)

            atualizarListaCliente()
        }

    }
    fun atualizarListaCliente(){

        viewModelScope.launch {

            listAll.value = repository.getAll()
        }

    }


    fun deletarTodasReceitas(id:MutableList<Long>){

        viewModelScope.launch {
            repository.deleteAll(id)
        }

    }

    fun getIdCliente(nome:String){

        viewModelScope.launch {

            ids.value = repository.getIdCliente(nome)

        }


    }

    fun getNomeCliente(){

        viewModelScope.launch {

            nomes.value = repository.getNomeCliente()

        }


    }


}