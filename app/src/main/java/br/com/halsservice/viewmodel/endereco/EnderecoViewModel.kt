package br.com.halsservice.viewmodel.endereco

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.halsservice.network.api.ApiHelper
import br.com.halsservice.state.EnderecoState
import br.com.halsservice.state.HalsServiceState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.ConnectException

/**
 * Created by RubioAlves on 16/04/2021
 */
class EnderecoViewModel(private val apiHelper: ApiHelper) : ViewModel() {

    private val _enderecoState = MutableStateFlow<EnderecoState>(EnderecoState.Empty)
    val enderecoState: StateFlow<EnderecoState> = _enderecoState

    fun buscaEnderecoPelo(cep: String) {

        viewModelScope.launch {

            try {

                _enderecoState.value = EnderecoState.Loading(true)
                delay(1000)

                val resposta = apiHelper.getEndereco(cep)

                if (resposta.isSuccessful && resposta.body() != null) {
                    _enderecoState.value = EnderecoState.Sucess(resposta.body())
                } else {
                    _enderecoState.value = EnderecoState.Error("CEP não encontrado")
                }

            } catch (e: Exception) {
                _enderecoState.value = EnderecoState.Error("Sem internet")
            }

            _enderecoState.value = EnderecoState.Loading(false)
        }


    }


}