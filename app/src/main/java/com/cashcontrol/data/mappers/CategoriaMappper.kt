package com.cashcontrol.data.mappers

import com.cashcontrol.data.local.entities.CategoriaEntity
import com.cashcontrol.data.remote.dto.CategoriaRequestDto
import com.cashcontrol.data.remote.dto.CategoriaResponseDto

fun CategoriaEntity.toResponse() = CategoriaResponseDto(
    categoriaId = this.categoriaId,
    tipo = this.tipo,
    descripcion = this.descripcion,
)

fun CategoriaEntity.toRequest() = CategoriaRequestDto(
    categoriaId = this.categoriaId,
    tipo = this.tipo,
    descripcion = this.descripcion,
    usuarioId = this.usuarioId,
)

fun CategoriaResponseDto.toEntity(userId: Long) = CategoriaEntity(
    categoriaId = this.categoriaId,
    usuarioId = userId,
    tipo = this.tipo,
    descripcion = this.descripcion
)

fun CategoriaRequestDto.toResponse() = CategoriaResponseDto(
    categoriaId = this.categoriaId,
    tipo = this.tipo,
    descripcion = this.descripcion
)