package com.cashcontrol.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.cashcontrol.data.local.entities.UsuarioEntity

@Dao
sealed interface UsuarioDao {
    @Upsert
    suspend fun save(user: UsuarioEntity)

    @Query("""
        select *
        from Usuarios
        where usuarioId = :id
    """)
    suspend fun getUsuarioById(id: Long?): UsuarioEntity?

    @Query("""
        select *
        from Usuarios
        where email = :email
    """)
    suspend fun getUsuarioByEmail(email: String): UsuarioEntity?
}