package br.com.halsservice.data.dao

import androidx.room.*
import br.com.halsservice.domain.Cliente
import br.com.halsservice.domain.ClienteAndServico
import br.com.halsservice.domain.Servico

@Dao
interface ClienteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cliente: Cliente)
    @Update
    suspend fun update(cliente: Cliente)
    @Query("DELETE FROM cliente WHERE clienteId IN (:id)")
    suspend fun deleteAll(id:MutableList<Long>)
    @Query("SELECT * FROM cliente" )
    suspend fun getAll(): MutableList<Cliente>
    @Transaction
    @Query("SELECT * FROM cliente" )
    suspend fun getClienteAndServico():MutableList<ClienteAndServico>


}
