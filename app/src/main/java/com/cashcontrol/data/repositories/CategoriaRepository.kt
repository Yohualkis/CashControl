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
    companion object{
        private const val DESCRIPCION_OCUPADA = "Esta decripción esta ocupada *"
        private const val ERROR_CONEXION = "Error de conexión"
        private const val ERROR_DESCONOCIDO = "Error inesperado"
        private const val ERROR_ELIMINAR = "Error al eliminar"
        private const val ERROR_CATEGORIAS_NOTFOUND = "Categorías no encontradas"
    }

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
                    emit(Resource.Error(DESCRIPCION_OCUPADA))
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
                    emit(Resource.Error(DESCRIPCION_OCUPADA))
                    return@flow
                }

                val categoriaResponse: CategoriaResponseDto = if (requestDto.categoriaId!! < 1)
                    remote.crearCategoria(requestDto)
                else
                    remote.editarCategoria(requestDto)

                categoriaLocal = categoriaResponse.toEntity(requestDto.usuarioId)
                categoriaDao.save(categoriaLocal)
            } catch (e: HttpException) {
                emit(Resource.Error(DESCRIPCION_OCUPADA))
            } catch (e: IOException) {
                emit(Resource.Error(ERROR_CONEXION))
            } catch (e: Exception) {
                emit(Resource.Error(ERROR_DESCONOCIDO))
            }

            val categoriaEntityRetornada = categoriaDao.find(requestDto.categoriaId)?.toResponse()

            emit(Resource.Success(categoriaEntityRetornada ?: requestDto.toResponse()))
        }
    }

    fun eliminarCategoria(categoriaId: Long?): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = remote.eliminarCategoria(categoriaId)
                categoriaDao.find(categoriaId)?.let {
                    categoriaDao.delete(it)
                    emit(Resource.Success(response))
                    return@flow
                }
                emit(Resource.Error(ERROR_ELIMINAR))
            } catch (e: HttpException) {
                emit(Resource.Error(ERROR_ELIMINAR))
            } catch (e: Exception) {
                emit(Resource.Error(ERROR_CONEXION))
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
                emit(Resource.Error(ERROR_CATEGORIAS_NOTFOUND))
            } catch (e: Exception) {
                emit(Resource.Error(ERROR_CONEXION))
            }

            listaLocal = categoriaDao.getByTipoAndUsuarioId(tipoCategoria, user?.usuarioId)
            val listaDto = listaLocal.map { it.toResponse() }
            emit(Resource.Success(listaDto))
        }
    }
}