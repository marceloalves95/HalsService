package br.com.halsservice.data.repository

import android.app.Application
import br.com.halsservice.data.dao.ServicoDao
import br.com.halsservice.data.db.AppDatabase
import br.com.halsservice.domain.Servico

/**
 * Created by RubioAlves on 13/04/2021
 */
class ServicoRepository(application: Application) {

    private val servicoDao: ServicoDao

    init {
        val database = AppDatabase.getDatabase(application)
        servicoDao = database.servicoDao()
    }

    suspend fun insert(servico: Servico){

        servicoDao.insert(servico)

    }

    suspend fun update(servico: Servico){

        servicoDao.update(servico)

    }

    suspend fun getAll():MutableList<Servico>{

        return servicoDao.getAll()

    }

    suspend fun deleteAll(id: MutableList<Long>){

        servicoDao.deleteAll(id)

    }
}