package br.com.halsservice.domain

/**
 * Hals Service
 * @author Marcelo Alves
 * 09/03/2021
 */
data class Servico(val id:Int,
                   val descricao:String,
                   val tipo_servico:String,
                   val valor_servico:String,
                   val garantia:String,
                   val data_reparo:String)