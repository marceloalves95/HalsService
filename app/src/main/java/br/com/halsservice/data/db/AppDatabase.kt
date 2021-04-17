package br.com.halsservice.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.halsservice.data.dao.ClienteDao
import br.com.halsservice.data.dao.ServicoDao
import br.com.halsservice.domain.Cliente
import br.com.halsservice.domain.Servico

/**
 * Created by RubioAlves on 13/04/2021
 */
@Database(entities = [Cliente::class, Servico::class],version = 3, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun clienteDao(): ClienteDao
    abstract fun servicoDao(): ServicoDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {

                return tempInstance

            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "hals-service.db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance

            }

        }


    }

}