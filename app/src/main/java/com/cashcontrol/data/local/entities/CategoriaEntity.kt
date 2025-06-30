package com.cashcontrol.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categorias")
data class CategoriaEntity(
    @PrimaryKey
    val categoriaId: Int?,
    val usuarioId: Int?,
    val tipo: String,
    val descripcion: String
)
