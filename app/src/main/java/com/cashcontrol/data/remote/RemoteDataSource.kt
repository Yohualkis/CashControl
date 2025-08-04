package com.cashcontrol.data.remote

import com.cashcontrol.data.remote.api.AutorizacionApi
import com.cashcontrol.data.remote.api.CategoriaApi
import com.cashcontrol.data.remote.api.TransaccionApi
import com.cashcontrol.data.remote.dto.AutorizacionRequestDto
import com.cashcontrol.data.remote.dto.CategoriaRequestDto
import com.cashcontrol.data.remote.dto.FiltroCategoriaRequestDto
import com.cashcontrol.data.remote.dto.FiltroTransaccionesRequestDto
import com.cashcontrol.data.remote.dto.TransaccionRequestDto
import com.cashcontrol.data.remote.dto.UsuarioRequestDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val categoriaApi: CategoriaApi,
    private val transaccionApi: TransaccionApi,
    private val autorizacionApi: AutorizacionApi,
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


    // TRANSACCIONES
    suspend fun getTransaccionesPorUsuarioIdYTipoCategoria(request: FiltroTransaccionesRequestDto) =
        transaccionApi.getTransaccionesPorUsuarioIdYTipoCategoria(
            usuarioId = request.usuarioId,
            tipoCategoria = request.tipoCategoria
        )

    suspend fun crearTransaccion(request: TransaccionRequestDto) =
        transaccionApi.postTransaccion(request)

    suspend fun editarTransaccion(request: TransaccionRequestDto) =
        transaccionApi.putTransaccion(request)

    suspend fun eliminarTransaccion(id: Long) =
        transaccionApi.deleteTransaccion(id)

    // AUTORIZACION
    suspend fun login(loginRequest: AutorizacionRequestDto) =
        autorizacionApi.login(loginRequest)

    suspend fun register(usuarioRequestDto: UsuarioRequestDto) =
        autorizacionApi.register(usuarioRequestDto)
}