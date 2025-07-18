package com.cashcontrol.di

import com.cashcontrol.data.remote.api.AutorizacionApi
import com.cashcontrol.data.remote.api.CategoriaApi
import com.cashcontrol.data.remote.interceptors.AutorizacionInterceptor
import com.cashcontrol.session.Sesion
import com.cashcontrol.util.TokenProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
//    const val BASE_URL = "https://cashcontrolapi.onrender.com/"
    const val BASE_URL = "http://10.0.2.2:8080/"
//    const val BASE_URL = "http://10.0.0.4:8080/"

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(DateAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideTokenProvider(): TokenProvider {
        return TokenProvider {
            Sesion.token
        }
    }

    @Provides
    @Singleton
    fun provideAutorizacionInterceptor(tokenProvider: TokenProvider): AutorizacionInterceptor {
        return AutorizacionInterceptor { tokenProvider.getToken() }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AutorizacionInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(providesMoshi()))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AutorizacionApi {
        return retrofit.create(AutorizacionApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoriaApi(moshi: Moshi): CategoriaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(CategoriaApi::class.java)
    }
}