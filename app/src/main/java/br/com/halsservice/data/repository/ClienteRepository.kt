package br.com.halsservice.data.repository

import android.app.Application
import br.com.halsservice.data.dao.ClienteDao
import br.com.halsservice.data.dao.ServicoDao
import br.com.halsservice.data.db.AppDatabase
import br.com.halsservice.domain.Cliente
import br.com.halsservice.domain.ClienteAndServico

/**
 * Created by RubioAlves on 13/04/2021
 */
class ClienteRepository(application: Application) {

    private val clienteDao:ClienteDao

    init {
        val database = AppDatabase.getDatabase(application)
        clienteDao = database.clienteDao()
    }

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

    suspend fun getClienteAndServico():MutableList<ClienteAndServico>{

        return clienteDao.getClienteAndServico()

    }

}