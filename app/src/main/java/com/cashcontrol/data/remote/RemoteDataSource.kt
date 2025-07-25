package com.cashcontrol.data.remote

import com.cashcontrol.data.remote.api.AutorizacionApi
import com.cashcontrol.data.remote.api.CategoriaApi
import com.cashcontrol.data.remote.dto.AutorizacionRequestDto
import com.cashcontrol.data.remote.dto.CategoriaRequestDto
import com.cashcontrol.data.remote.dto.FiltroCategoriaRequestDto
import com.cashcontrol.data.remote.dto.UsuarioRequestDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val categoriaApi: CategoriaApi,
    private val autorizacionApi: AutorizacionApi
) {
    // CATEGORIAS
    suspend fun crearCategoria(requestCategoriaDto: CategoriaRequestDto) =
        categoriaApi.postCategoria(requestCategoriaDto)

    suspend fun editarCategoria(requestCategoriaDto: CategoriaRequestDto) =
        categoriaApi.putCategoria(requestCategoriaDto)

    suspend fun eliminarCategoria(id: Long?) =
        categoriaApi.deleteCategoria(id)

    suspend fun getCategoriaPorUsuarioYTipo(request: FiltroCategoriaRequestDto) =
        categoriaApi.getCategoriaPorUsuarioYTipo(request)

    // AUTORIZACION
    suspend fun login(loginRequest: AutorizacionRequestDto) = autorizacionApi.login(loginRequest)
    suspend fun register(usuarioRequestDto: UsuarioRequestDto) = autorizacionApi.register(usuarioRequestDto)
}