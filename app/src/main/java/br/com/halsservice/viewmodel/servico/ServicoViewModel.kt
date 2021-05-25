package br.com.halsservice.viewmodel.servico

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.halsservice.data.repository.ServicoRepository
import br.com.halsservice.domain.ClienteAndServico
import br.com.halsservice.domain.Servico
import kotlinx.coroutines.launch

class ServicoViewModel(private val repository:ServicoRepository) : ViewModel() {

    val nomes = MutableLiveData<MutableList<String>>()
    val listAll = MutableLiveData<MutableList<Servico>>()
    val listAllTransaction = MutableLiveData<MutableList<ClienteAndServico>>()

    fun adicionarServico(servico: Servico){

        viewModelScope.launch {
            repository.insert(servico)

            atualizarLista()

        }

    }
    fun atualizarLista(){

        viewModelScope.launch {
            listAll.value = repository.getAll()
        }

    }

    fun deletarAll(id:MutableList<Long>){

        viewModelScope.launch {

            repository.deleteAll(id)

        }


    }

    fun atualizarListaCompleta(){

        viewModelScope.launch {
            listAllTransaction.value = repository.getClienteAndServico()
        }

    }

    fun getNomesClientes(){

        viewModelScope.launch {

            nomes.value = repository.getNomeCliente()

        }


    }



}