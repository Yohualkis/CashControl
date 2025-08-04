package com.cashcontrol.presentation.categoria

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cashcontrol.data.local.entities.CategoriaEntity
import com.cashcontrol.data.mappers.toEntity
import com.cashcontrol.data.remote.Resource
import com.cashcontrol.data.remote.dto.CategoriaRequestDto
import com.cashcontrol.data.repositories.AutorizacionRepository
import com.cashcontrol.data.repositories.CategoriaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriaViewModel @Inject constructor(
    private val categoriaRepo: CategoriaRepository,
    private val autorizacionRepo: AutorizacionRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoriaUiState())
    val uiState = _uiState.asStateFlow()

    init{
        getCategoriasPorTipo("GASTOS")
    }
    
    fun onEvent(event: CategoriaEvent) {
        when (event) {
            is CategoriaEvent.DescripcionChange -> onDescripcionChange(event.descripcion)
            is CategoriaEvent.TipoCategoriaChange -> onTipoCategoriaChange(event.tipoCategoria)

            is CategoriaEvent.Delete -> delete(event.categoria)
            CategoriaEvent.Save -> save()
            CategoriaEvent.Limpiar -> limpiar()
        }
    }

    fun selectedCategoria(categoriaId: Long) {
        viewModelScope.launch {
            if (categoriaId > 0) {
                val categoria = categoriaRepo.findCategoria(categoriaId)
                _uiState.update {
                    it.copy(
                        categoriaId = categoria?.categoriaId,
                        descripcion = categoria?.descripcion
                    )
                }
            }
        }
    }

    private fun limpiar() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    errorDescripcion = "",
                    errorTipoCategoria = "",
                    errorGeneral = "",
                )
            }
        }
    }

    private fun save() {
        viewModelScope.launch {
            limpiar()
            if (errorCampos())
                return@launch

            categoriaRepo.saveCategoria(
                CategoriaRequestDto(
                    categoriaId = uiState.value.categoriaId,
                    usuarioId = autorizacionRepo.getUser()?.usuarioId ?: 0,
                    tipo = uiState.value.tipoCategoria ?: "",
                    descripcion = uiState.value.descripcion ?: ""
                )
            )
                .collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    errorGeneral = result.message,
                                    isLoading = false,
                                )
                            }
                        }

                        is Resource.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorGeneral = ""
                                )
                            }
                        }

                        is Resource.Loading -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = true
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun delete(categoria: CategoriaEntity) {
        viewModelScope.launch {
            var seEliminoCategoria = false
            categoriaRepo.eliminarCategoria(categoria.categoriaId)
                .collectLatest { result ->
                    when(result){
                        is Resource.Error -> _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorGeneral = result.message
                            )
                        }

                        is Resource.Loading -> _uiState.update {
                            it.copy(isLoading = true)
                        }

                        is Resource.Success -> {
                            _uiState.update {
                                it.copy(isLoading = false)
                            }
                            seEliminoCategoria = result.data == true
                        }
                    }
                }
            if(seEliminoCategoria) {
                getCategoriasPorTipo(categoria.tipo)
            }
        }
    }

    fun getCategoriasPorTipo(tipo: String) {
        viewModelScope.launch {
            categoriaRepo.getCategoriasPorTipoYUsuario(
                tipo = tipo,
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorGeneral = result.message,
                                isLoading = false,
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                listaCategoria = result.data?.map {
                                    it.toEntity(autorizacionRepo.getUser()?.usuarioId ?: return@collectLatest)
                                } ?: emptyList()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun errorCampos(): Boolean {
        var errorEncontrado = false
        if (_uiState.value.descripcion.isNullOrBlank()) {
            _uiState.update {
                it.copy(errorDescripcion = "Este campo es obligatorio *")
            }
            errorEncontrado = true
        }
        if (_uiState.value.tipoCategoria.isNullOrBlank()) {
            _uiState.update {
                it.copy(errorTipoCategoria = "Este campo es obligatorio *")
            }
            errorEncontrado = true
        }
        return errorEncontrado
    }

    private fun onTipoCategoriaChange(tipoCategoria: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    tipoCategoria = tipoCategoria,
                    errorTipoCategoria = "",
                )
            }
        }
    }

    private fun onDescripcionChange(descripcion: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    descripcion = descripcion,
                    errorDescripcion = "",
                )
            }
        }
    }
}