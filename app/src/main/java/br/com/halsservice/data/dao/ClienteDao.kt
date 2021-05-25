package br.com.halsservice.data.dao

import androidx.room.*
import br.com.halsservice.domain.Cliente
import br.com.halsservice.domain.ClienteAndServico

@Dao
interface ClienteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cliente: Cliente)
    @Update
    suspend fun update(cliente: Cliente)
    @Query("DELETE FROM cliente WHERE clienteId IN (:id)")
    suspend fun deleteAll(id:MutableList<Long>)
    @Query("SELECT * FROM cliente")
    suspend fun getAll(): MutableList<Cliente>
    @Query("SELECT clienteId FROM cliente WHERE nome = :nome")
    suspend fun getIdCliente(nome:String):Long
    @Query("SELECT nome FROM cliente")
    suspend fun getNomesClientes():MutableList<String>



}
