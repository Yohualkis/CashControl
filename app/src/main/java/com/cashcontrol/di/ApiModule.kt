package com.cashcontrol.di

import com.cashcontrol.data.remote.api.AutorizacionApi
import com.cashcontrol.data.remote.api.CategoriaApi
import com.cashcontrol.data.remote.api.TransaccionApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    //    const val BASE_URL = "https://cashcontrolapi.onrender.com/"
//    const val BASE_URL = "http://10.0.2.2:8080/"
    const val BASE_URL = "http://10.0.0.39:8080/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(DateAdapter())
            .build()

    @Provides
    @Singleton
    fun provideCategoriaApi(): CategoriaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
            .build()
            .create(CategoriaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAutorizacionApi(): AutorizacionApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
            .build()
            .create(AutorizacionApi::class.java)
    }
    
    @Provides
    @Singleton
    fun provideTransaccionApi(): TransaccionApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
            .build()
            .create(TransaccionApi::class.java)

}