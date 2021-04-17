package br.com.halsservice.domain

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by RubioAlves on 13/04/2021
 */
data class ClienteAndServico(
    @Embedded
    val cliente:Cliente,
    @Relation(parentColumn = "clienteId", entityColumn = "fk_cliente")
    val servico:List<Servico> = emptyList()

)