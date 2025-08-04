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
    fun saveCategoria(requestDto: CategoriaRequestDto): Flow<Resource<CategoriaResponseDto>> {
        return flow {
            emit(Resource.Loading())
            var categoriaLocal: CategoriaEntity? = null

            val listaTodasLasCategoriasLocales = categoriaDao.getAll()
            listaTodasLasCategoriasLocales.forEach { cat ->
                if (cat.descripcion == requestDto.descripcion &&
                    cat.tipo == requestDto.tipo &&
                    cat.categoriaId != requestDto.categoriaId
                ) {
                    emit(Resource.Error("Esta descripción está ocupada *"))
                    return@flow
                }
            }

            try {
                val listaCategoriasFiltradasFetched = remote.getCategoriaPorUsuarioYTipo(
                    request = FiltroCategoriaRequestDto(
                        tipo = requestDto.tipo,
                        usuarioId = requestDto.usuarioId
                    )
                )

                listaCategoriasFiltradasFetched.forEach { cat ->
                    if (cat.descripcion == requestDto.descripcion &&
                        cat.tipo == requestDto.tipo &&
                        cat.categoriaId != requestDto.categoriaId
                    ) {
                        emit(Resource.Error("Esta descripción está ocupada *"))
                        return@flow
                    }
                }

                val categoriaResponse: CategoriaResponseDto = if (requestDto.categoriaId!! < 1)
                    remote.crearCategoria(requestDto)
                else
                    remote.editarCategoria(requestDto)

                categoriaLocal = categoriaResponse.toEntity(requestDto.usuarioId)
                categoriaDao.save(categoriaLocal)
            } catch (e: HttpException) {
                emit(Resource.Error("Esta descripción está ocupada *"))
            } catch (e: IOException) {
                emit(Resource.Error("Error de conexión"))
            } catch (e: Exception) {
                emit(Resource.Error("Error inesperado"))
            }

            val categoriaEntityRetornada = categoriaDao.find(requestDto.categoriaId)?.toResponse()

            if (categoriaEntityRetornada == null)
                emit(Resource.Success(requestDto.toResponse()))
            else
                emit(Resource.Success(categoriaEntityRetornada))
        }
    }

    fun eliminarCategoria(categoriaId: Long?): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            var categoriaLocal: CategoriaEntity? = null
            try {
                val response = remote.eliminarCategoria(categoriaId)
                if (response != true) {
                    emit(Resource.Error("Error al eliminar"))
                    return@flow
                }
                categoriaLocal = categoriaDao.find(categoriaId)
                categoriaDao.delete(categoriaLocal!!)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                categoriaLocal = categoriaDao.find(categoriaId)
                if (categoriaLocal != null)
                    categoriaDao.delete(categoriaLocal)
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