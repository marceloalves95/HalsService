package br.com.halsservice.domain

/**
 * Hals Service
 * @author Marcelo Alves
 * 09/03/2021
 */
data class Cliente(
        val id:Int,
        val nome:String,
        val endereco:String,
        val numero:Int,
        val bairro:String,
        val telefone:String)