package com.cashcontrol.data.repositories

import com.cashcontrol.data.local.dao.TransaccionDao
import com.cashcontrol.data.local.entities.TransaccionEntity
import com.cashcontrol.data.mappers.toEntity
import com.cashcontrol.data.mappers.toResponse
import com.cashcontrol.data.remote.RemoteDataSource
import com.cashcontrol.data.remote.Resource
import com.cashcontrol.data.remote.dto.FiltroTransaccionesRequestDto
import com.cashcontrol.data.remote.dto.TransaccionRequestDto
import com.cashcontrol.data.remote.dto.TransaccionResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class TransaccionRepository @Inject constructor(
    private val transaccionDao: TransaccionDao,
    private val catRepo: CategoriaRepository,
    private val remote: RemoteDataSource,
) {
    fun saveTransaccion(requestDto: TransaccionRequestDto): Flow<Resource<TransaccionResponseDto>> {
        return flow {
            emit(Resource.Loading())
            var categoria = catRepo.findCategoria(requestDto.categoriaId)

            transaccionDao.getTransaccionesPorUsuarioYCategoria(
                usuarioId = requestDto.usuarioId,
                tipoCategoria = categoria!!.tipo
            ).forEach { tran ->
                if (tran.descripcion == requestDto.descripcion &&
                    tran.transaccionId != requestDto.transaccionId
                ) {
                    emit(Resource.Error("Esta descripción está ocupada *"))
                    return@flow
                }
            }

            try {
                val listaTransaccionesFetched = remote.getTransaccionesPorUsuarioIdYTipoCategoria(
                    request = FiltroTransaccionesRequestDto(
                        tipoCategoria = categoria.tipo,
                        usuarioId = requestDto.transaccionId
                    )
                )

                listaTransaccionesFetched.forEach { tran ->
                    if (tran.descripcion == requestDto.descripcion &&
                        tran.transaccionId != requestDto.transaccionId
                    ) {
                        emit(Resource.Error("Esta descripción está ocupada *"))
                        return@flow
                    }
                }

                val transaccionResponse = if (requestDto.transaccionId < 1)
                    remote.crearTransaccion(requestDto)
                else {
                    remote.editarTransaccion(requestDto)
                }

                transaccionDao.save(transaccionResponse.toEntity())
            } catch (e: IOException) {
                emit(Resource.Error("Error de conexión"))
            } catch (e: Exception) {
                emit(Resource.Error("Error inesperado"))
            }
        }
    }

    fun eliminarTransaccion(transaccionId: Long): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            var transaccionLocal: TransaccionEntity? = null
            try {
                val response = remote.eliminarTransaccion(transaccionId)
                if (response != true) {
                    emit(Resource.Error("Error al eliminar"))
                    return@flow
                }
                transaccionLocal = transaccionDao.find(transaccionId)
                transaccionDao.delete(transaccionLocal!!)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                emit(Resource.Error("Error al eliminar"))
            } catch (e: Exception) {
                emit(Resource.Error("Error de conexión"))
            }
        }
    }

    fun getTransaccionesPorUsuarioIdYTipoCategoria(
        usuarioId: Long,
        tipoCategoria: String,
    ): Flow<Resource<List<TransaccionResponseDto>>> {
        return flow {
            emit(Resource.Loading())
            var listaLocal: List<TransaccionEntity> = emptyList()
            try {
                val listaTransaccionesFetched =
                    remote.getTransaccionesPorUsuarioIdYTipoCategoria(
                        FiltroTransaccionesRequestDto(
                            tipoCategoria = tipoCategoria,
                            usuarioId = usuarioId,
                        )
                    )

                val listaLocal = listaTransaccionesFetched.map {
                    it.toEntity()
                }
                transaccionDao.saveLista(listaLocal)
            } catch (e: HttpException) {
                emit(Resource.Error("Categorías no encontradas"))
            } catch (e: Exception) {
                emit(Resource.Error("Error de conexión"))
            }

            listaLocal =
                transaccionDao.getTransaccionesPorUsuarioYCategoria(usuarioId, tipoCategoria)
            val listaDto = listaLocal.map { it.toResponse() }
            emit(Resource.Success(listaDto))
        }
    }

    suspend fun findTransaccion(transaccionId: Long) = transaccionDao.find(transaccionId)
}