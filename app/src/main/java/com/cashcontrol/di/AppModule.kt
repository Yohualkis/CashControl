package com.cashcontrol.di

import android.content.Context
import androidx.room.Room
import com.cashcontrol.data.local.database.CashControlDb
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideCashControlDb(@ApplicationContext contexto: Context) =
        Room.databaseBuilder(
            context = contexto,
            klass = CashControlDb::class.java,
            name = "CashControl.db"
        ).fallbackToDestructiveMigration(true)
            .build()

    @Provides
    fun provideTransaccionDao(ccdb: CashControlDb) = ccdb.TransaccionDao()

    @Provides
    fun provideUsuarioDao(ccdb: CashControlDb) = ccdb.UsuarioDao()

    @Provides
    fun provideMetaDao(ccdb: CashControlDb) = ccdb.MetaDao()

    @Provides
    fun provideCategoriaDao(ccdb: CashControlDb) = ccdb.CategoriaDao()

    @Provides
    fun provideSugerenciaDao(ccdb: CashControlDb) = ccdb.SugerenciaDao()
}