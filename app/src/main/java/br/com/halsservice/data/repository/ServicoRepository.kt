package br.com.halsservice.data.repository

import br.com.halsservice.data.dao.ServicoDao
import br.com.halsservice.domain.ClienteAndServico
import br.com.halsservice.domain.Servico

/**
 * Created by RubioAlves on 13/04/2021
 */
class ServicoRepository(private val servicoDao: ServicoDao) {

    suspend fun insert(servico: Servico) = servicoDao.insert(servico)
    suspend fun update(servico: Servico) = servicoDao.update(servico)
    suspend fun getAll():MutableList<Servico> = servicoDao.getAll()
    suspend fun deleteAll(id: MutableList<Long>) = servicoDao.deleteAll(id)
    suspend fun getClienteAndServico():MutableList<ClienteAndServico> = servicoDao.getClienteAndServico()
    suspend fun getNomeCliente():MutableList<String> = servicoDao.getNomeCliente()

}