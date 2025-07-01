package com.cashcontrol.di

import android.content.Context
import androidx.room.Room
import com.cashcontrol.data.local.database.CashControlDb
import dagger.Module
import dagger.Provides
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
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideTransaccionDao(ccdb: CashControlDb) = ccdb.TransaccionDao()

    @Provides
    fun provideCategoriaDao(ccdb: CashControlDb) = ccdb.CategoriaDao()
}