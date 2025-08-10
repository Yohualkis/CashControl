package com.cashcontrol.data.repositories

import com.cashcontrol.data.local.dao.CategoriaDao
import com.cashcontrol.data.local.entities.CategoriaEntity
import com.cashcontrol.data.mappers.toEntity
import com.cashcontrol.data.mappers.toResponse
import com.cashcontrol.data.remote.RemoteDataSource
import com.cashcontrol.data.remote.Resource
import com.cashcontrol.data.remote.dto.CategoriaRequestDto
import com.cashcontrol.data.remote.dto.CategoriaResponseDto
import com.cashcontrol.data.remote.dto.FiltroCategoriaRequestDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class CategoriaRepository @Inject constructor(
    private val remote: RemoteDataSource,
    private val categoriaDao: CategoriaDao,
    private val authRepo: AutorizacionRepository,
) {
    private val errorDescripcionOcupada = "Esta descripción está ocupada *"

    fun saveCategoria(requestDto: CategoriaRequestDto): Flow<Resource<CategoriaResponseDto>> {
        return flow {
            emit(Resource.Loading())
            var categoriaLocal: CategoriaEntity? = null

            categoriaDao
                .getAll()
                .firstOrNull {
                    it.descripcion == requestDto.descripcion &&
                            it.tipo == requestDto.tipo &&
                            it.categoriaId != requestDto.categoriaId
                }?.let {
                    emit(Resource.Error(errorDescripcionOcupada))
                    return@flow
                }

            try {
                remote.getCategoriaPorUsuarioYTipo(
                    request = FiltroCategoriaRequestDto(
                        tipo = requestDto.tipo,
                        usuarioId = requestDto.usuarioId
                    )
                ).firstOrNull {
                    it.descripcion == requestDto.descripcion &&
                            it.tipo == requestDto.tipo &&
                            it.categoriaId != requestDto.categoriaId
                }?.let {
                    emit(Resource.Error(errorDescripcionOcupada))
                    return@flow
                }

                val categoriaResponse: CategoriaResponseDto = if (requestDto.categoriaId!! < 1)
                    remote.crearCategoria(requestDto)
                else
                    remote.editarCategoria(requestDto)

                categoriaLocal = categoriaResponse.toEntity(requestDto.usuarioId)
                categoriaDao.save(categoriaLocal)
            } catch (e: HttpException) {
                emit(Resource.Error(errorDescripcionOcupada))
            } catch (e: IOException) {
                emit(Resource.Error("Error de conexión"))
            } catch (e: Exception) {
                emit(Resource.Error("Error inesperado"))
            }

            val categoriaEntityRetornada = categoriaDao.find(requestDto.categoriaId)?.toResponse()

            emit(Resource.Success(categoriaEntityRetornada ?: requestDto.toResponse()))
        }
    }

    fun eliminarCategoria(categoriaId: Long?): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            var categoriaLocal: CategoriaEntity? = null
            try {
                val response = remote.eliminarCategoria(categoriaId)
                if (response == true) {
                    emit(Resource.Error("Error al eliminar"))
                    return@flow
                }
                categoriaLocal = categoriaDao.find(categoriaId)
                categoriaDao.delete(categoriaLocal!!)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                emit(Resource.Error("Error al eliminar"))
            } catch (e: Exception) {
                emit(Resource.Error("Error de conexión"))
            }
        }
    }

    suspend fun getAll() = categoriaDao.getAll()

    suspend fun findCategoria(id: Long) = categoriaDao.find(id)

    fun getCategoriasPorTipoYUsuario(tipo: String): Flow<Resource<List<CategoriaResponseDto>>> {
        return flow {
            emit(Resource.Loading())
            var tipoCategoria = tipo.uppercase()
            val user = authRepo.getUser()
            var listaLocal: List<CategoriaEntity> = emptyList()
            try {
                val listaCategoriasFiltradasFetched = remote.getCategoriaPorUsuarioYTipo(
                    FiltroCategoriaRequestDto(
                        tipo = tipoCategoria,
                        usuarioId = user?.usuarioId ?: 0
                    )
                )
                val listaLocal = listaCategoriasFiltradasFetched.map {
                    it.toEntity(user?.usuarioId ?: 0)
                }
                categoriaDao.saveLista(listaLocal)
            } catch (e: HttpException) {
                emit(Resource.Error("Categorías no encontradas"))
            } catch (e: Exception) {
                emit(Resource.Error("Error de conexión"))
            }

            listaLocal = categoriaDao.getByTipoAndUsuarioId(tipoCategoria, user?.usuarioId)
            val listaDto = listaLocal.map { it.toResponse() }
            emit(Resource.Success(listaDto))
        }
    }
}