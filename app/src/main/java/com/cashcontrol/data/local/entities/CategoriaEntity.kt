package com.cashcontrol.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categorias")
data class CategoriaEntity(
    @PrimaryKey
    val categoriaId: Long?,
    val usuarioId: Long?,
    val tipo: String,
    val descripcion: String
)
