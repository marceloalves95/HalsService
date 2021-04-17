package br.com.halsservice.data.dao

import androidx.room.*
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

}
