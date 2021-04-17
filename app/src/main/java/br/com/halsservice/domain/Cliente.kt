package br.com.halsservice.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Hals Service
 * @author Marcelo Alves
 * 09/03/2021
 */
@Entity(tableName = "cliente", indices = [Index(value = ["telefone"], unique = true)] )
data class Cliente(
        @PrimaryKey(autoGenerate = true)
        val clienteId:Long,
        val nome:String,
        val endereco:String,
        val numero:Int,
        val bairro:String,
        val estado:String,
        val telefone:String,
        var selected:Boolean)