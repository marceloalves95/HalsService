package br.com.halsservice.framework.viewmodel.servico

import android.app.Application
import androidx.lifecycle.*
import br.com.halsservice.data.repository.ClienteRepository
import br.com.halsservice.data.repository.ServicoRepository
import br.com.halsservice.domain.Cliente
import br.com.halsservice.domain.Servico
import kotlinx.coroutines.launch

class ServicoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ServicoRepository(application)
    private val listAll = MutableLiveData<MutableList<Servico>>()

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


}