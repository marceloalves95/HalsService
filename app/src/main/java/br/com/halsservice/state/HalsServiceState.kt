package br.com.halsservice.state

import br.com.halsservice.domain.Cliente

/**
 * Created by RubioAlves on 09/05/2021
 */
sealed class HalsServiceState{
    class Error(val message: String):HalsServiceState()
    class Loading(val isLoading: Boolean):HalsServiceState()
    class Sucess(val message: String):HalsServiceState()
    object Empty : HalsServiceState()
}

