package br.com.halsservice.domain

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Hals Service
 * @author Marcelo Alves
 * 09/03/2021
 */
@Entity(
        tableName = "servico",
        foreignKeys = [ForeignKey(
                onDelete = CASCADE,
                entity = Cliente::class,
                parentColumns = ["clienteId"],
                childColumns = ["fk_cliente"])],
        indices = [Index("fk_cliente")]
)
data class Servico(
    @PrimaryKey(autoGenerate = true)
    val servicoId:Long,
    val descricao:String,
    val tipo_servico:String,
    val valor_servico:Double,
    val garantia:String,
    val data_reparo:String,
    val fk_cliente:Long,
    var selected:Boolean
)