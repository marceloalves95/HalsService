package br.com.halsservice.framework.service

import br.com.halsservice.framework.api.HalsServiceApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by RubioAlves on 14/04/2021
 */
object RetrofitBuilder {

    private const val BASE_URL = "https://viacep.com.br/ws/"
//"https://cep.awesomeapi.com.br/json/"
    private fun getRetrofit(): Retrofit {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val halsServiceApi:HalsServiceApi = getRetrofit().create(HalsServiceApi::class.java)
}