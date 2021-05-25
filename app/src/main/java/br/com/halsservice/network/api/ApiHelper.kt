package br.com.halsservice.network.api

/**
 * Created by RubioAlves on 16/04/2021
 */
class ApiHelper (private val api:HalsServiceApi) {

    suspend fun getEndereco(cep:String) = api.buscaEndereco(cep)

}