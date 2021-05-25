package br.com.halsservice.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.halsservice.data.dao.ClienteDao
import br.com.halsservice.data.dao.ServicoDao
import br.com.halsservice.domain.Cliente
import br.com.halsservice.domain.Servico

/**
 * Created by RubioAlves on 13/04/2021
 */
@Database(entities = [Cliente::class, Servico::class],version = 5, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun clienteDao(): ClienteDao
    abstract fun servicoDao(): ServicoDao

}