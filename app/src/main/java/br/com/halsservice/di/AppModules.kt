package br.com.halsservice.di

import androidx.room.Room
import br.com.halsservice.data.db.AppDatabase
import br.com.halsservice.data.repository.ClienteRepository
import br.com.halsservice.data.repository.EnderecoRepository
import br.com.halsservice.data.repository.ServicoRepository
import br.com.halsservice.network.api.ApiHelper
import br.com.halsservice.network.service.RetrofitBuilder
import br.com.halsservice.utils.others.Constants.BD_NAME
import br.com.halsservice.viewmodel.cliente.ClienteViewModel
import br.com.halsservice.viewmodel.endereco.EnderecoViewModel
import br.com.halsservice.viewmodel.servico.ServicoViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by RubioAlves on 09/05/2021
 */

val appModules = module {

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            BD_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }
    single{
        get<AppDatabase>().clienteDao()
    }
    single {
        get<AppDatabase>().servicoDao()
    }
    factory {
        RetrofitBuilder.getRetrofit()
    }
    single {
        RetrofitBuilder.halsServiceApi(get())
    }
    single {
        ApiHelper(get())
    }
    single {
        ClienteRepository(get())
    }
    single {
        EnderecoRepository(get())
    }
    single {
        ServicoRepository(get())
    }
    viewModel {
        ClienteViewModel(get())
    }
    viewModel {
        EnderecoViewModel(get())
    }
    viewModel {
        ServicoViewModel(get())
    }


}
