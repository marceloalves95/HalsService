package br.com.halsservice.network.api

import br.com.halsservice.domain.CEP
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by RubioAlves on 14/04/2021
 */
interface HalsServiceApi {

    @GET("{CEP}/json")
    suspend fun buscaEndereco(@Path("CEP") cep: String): Response<CEP>

}