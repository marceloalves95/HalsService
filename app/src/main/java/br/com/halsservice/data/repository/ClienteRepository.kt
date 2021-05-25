package br.com.halsservice.data.repository

import br.com.halsservice.data.dao.ClienteDao
import br.com.halsservice.domain.Cliente
import br.com.halsservice.domain.ClienteAndServico

/**
 * Created by RubioAlves on 13/04/2021
 */
class ClienteRepository(private val clienteDao:ClienteDao) {

    suspend fun insert(cliente: Cliente){

        clienteDao.insert(cliente)

    }

    suspend fun update(cliente: Cliente){

        clienteDao.update(cliente)

    }

    suspend fun getAll():MutableList<Cliente>{

        return clienteDao.getAll()

    }

    suspend fun deleteAll(id: MutableList<Long>){

        clienteDao.deleteAll(id)

    }



    suspend fun getIdCliente(nome:String):Long{

        return clienteDao.getIdCliente(nome)

    }

    suspend fun getNomeCliente():MutableList<String>{

        return clienteDao.getNomesClientes()

    }








}