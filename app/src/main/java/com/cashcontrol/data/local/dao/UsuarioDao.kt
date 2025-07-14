package com.cashcontrol.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.cashcontrol.data.local.entities.UsuarioEntity

@Dao
sealed interface UsuarioDao {
    @Upsert
    suspend fun saveUser(user: UsuarioEntity)

    @Query("delete from Usuarios")
    suspend fun deleteUser()

    @Query("""
        select *
        from Usuarios
        limit 1
    """)
    suspend fun getUser(): UsuarioEntity?
}