package br.com.halsservice.state

import br.com.halsservice.domain.CEP
import retrofit2.Response

/**
 * Created by RubioAlves on 09/05/2021
 */
sealed class EnderecoState{
    class Error(val message: String):EnderecoState()
    class Loading(val isLoading: Boolean):EnderecoState()
    class Sucess(val response: CEP?) : EnderecoState()
    object Empty : EnderecoState()
}
