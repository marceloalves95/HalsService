package br.com.halsservice.network.service

import br.com.halsservice.network.api.HalsServiceApi
import br.com.halsservice.utils.others.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by RubioAlves on 14/04/2021
 */
object RetrofitBuilder {

    fun getRetrofit(): Retrofit {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun halsServiceApi(retrofit: Retrofit):HalsServiceApi = getRetrofit().create(HalsServiceApi::class.java)
}