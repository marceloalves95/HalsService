package br.com.halsservice.data.dao

import androidx.room.*
import br.com.halsservice.domain.ClienteAndServico
import br.com.halsservice.domain.Servico

@Dao
interface ServicoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(servico: Servico)
    @Update
    suspend fun update(servico: Servico)
    @Query("SELECT * FROM servico" )
    suspend fun getAll(): MutableList<Servico>
    @Query("DELETE FROM servico WHERE servicoId IN (:id)")
    suspend fun deleteAll(id:MutableList<Long>)
    @Transaction
    @Query("SELECT * FROM cliente")
    suspend fun getClienteAndServico():MutableList<ClienteAndServico>
    @Query("SELECT nome FROM cliente INNER JOIN servico ON clienteId = fk_cliente")
    suspend fun getNomeCliente():MutableList<String>


}
