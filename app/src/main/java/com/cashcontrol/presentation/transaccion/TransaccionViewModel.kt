package com.cashcontrol.presentation.transaccion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cashcontrol.data.local.entities.TransaccionEntity
import com.cashcontrol.data.mappers.toEntity
import com.cashcontrol.data.remote.Resource
import com.cashcontrol.data.remote.dto.TransaccionRequestDto
import com.cashcontrol.data.repositories.AutorizacionRepository
import com.cashcontrol.data.repositories.CategoriaRepository
import com.cashcontrol.data.repositories.TransaccionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransaccionViewModel @Inject constructor(
    private val transRepo: TransaccionRepository,
    private val catRepo: CategoriaRepository,
    private val authRepo: AutorizacionRepository,
) : ViewModel() {

    companion object {
        private const val REQUIRED_FIELD = "Este campo es obligatorio *"
    }

    private val _uiState = MutableStateFlow(TransaccionUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTransacciones("GASTOS")
        getAllCategorias()
    }

    fun onEvent(event: TransaccionEvent) {
        when (event) {
            is TransaccionEvent.CategoriaChange -> onCategoriaChage(
                event.categoriaId,
                event.descripcion
            )

            is TransaccionEvent.DescripcionTransaccionChange -> onDescripcionTransaccionChange(event.descripcionTransaccion)
            is TransaccionEvent.MontoChange -> onMontoChange(event.monto)
            is TransaccionEvent.UsarEnSugerenciaChange -> onUsarEnSugerenciaChange(event.usarEnSugerenciaChange)

            is TransaccionEvent.GetTransacciones -> {
                getTransacciones(event.tipoCategoria)
                getAllCategorias()
            }

            is TransaccionEvent.Delete -> onDelete(event.transaccion)
            TransaccionEvent.Save -> onSave()
            TransaccionEvent.Limpiar -> onLimpiar()
        }
    }

    fun getAllCategorias() =
        viewModelScope.launch {
            _uiState.update {
                it.copy(listaAllCategorias = catRepo.getAll())
            }
        }

    fun selectedTransaccion(transaccionId: Long) {
        viewModelScope.launch {
            if (transaccionId > 0) {
                val transaccion = transRepo.findTransaccion(transaccionId)
                val categoria = catRepo.findCategoria(transaccion?.categoriaId ?: return@launch)
                _uiState.update {
                    it.copy(
                        categoriaId = transaccion.categoriaId,
                        transaccionId = transaccion.transaccionId,
                        tipoCategoria = categoria?.tipo,
                        descripcionCategoria = categoria?.descripcion,
                        descripcionTransaccion = transaccion.descripcion,
                        fechaTransaccion = transaccion.fechaTransaccion,
                        monto = transaccion.monto,
                        usarEnSugerencia = transaccion.usarSugerencia,
                    )
                }
            }
        }
    }

    private fun getCategoriasPorTipo(tipo: String) {
        viewModelScope.launch {
            catRepo
                .getCategoriasPorTipoYUsuario(tipo)
                .collectLatest { result ->
                    when (result) {
                        is Resource.Error -> _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorGeneral = result.message
                            )
                        }

                        is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }

                        is Resource.Success -> _uiState.update {
                            it.copy(
                                listaCategoriasFiltradas = result.data?.map {
                                    it.toEntity(authRepo.getUser()?.usuarioId ?: 0)
                                } ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }

    private fun getTransacciones(tipoCategoria: String) {
        viewModelScope.launch {
            getCategoriasPorTipo(tipoCategoria)
            transRepo.getTransaccionesPorUsuarioIdYTipoCategoria(
                usuarioId = authRepo.getUser()?.usuarioId ?: 0,
                tipoCategoria = tipoCategoria
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorGeneral = result.message
                        )
                    }

                    is Resource.Loading -> _uiState.update {
                        it.copy(
                            isLoading = true,
                        )
                    }

                    is Resource.Success -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            listaTransacciones = result.data?.map {
                                it.toEntity()
                            } ?: emptyList()
                        )
                    }
                }
            }
        }
    }

    private fun errorCampos(): Boolean {
        var errorEncontrado = false
        _uiState.value.monto?.let {
            if (it <= 0.0) {
                _uiState.update {
                    it.copy(errorMonto = "Este campo debe ser mayor a cero *")
                }
                errorEncontrado = true
            }
        }

        if (_uiState.value.descripcionTransaccion.isNullOrBlank()) {
            _uiState.update {
                it.copy(errorDescripcionTransaccion = REQUIRED_FIELD)
            }
            errorEncontrado = true
        } else if (_uiState.value.descripcionTransaccion!!.length > 24) {
            _uiState.update {
                it.copy(errorDescripcionTransaccion = "Este campo no puede contener más de 24 caracteres *")
            }
        }

        if (_uiState.value.descripcionCategoria.isNullOrBlank()) {
            _uiState.update {
                it.copy(errorCategoria = REQUIRED_FIELD)
            }
            errorEncontrado = true
        } else if (_uiState.value.categoriaId == null) {
            _uiState.update {
                it.copy(errorCategoria = REQUIRED_FIELD)
            }
            errorEncontrado = true
        }

        return errorEncontrado
    }

    private fun onSave() {
        viewModelScope.launch {
            onLimpiar()
            if (errorCampos())
                return@launch

            transRepo.saveTransaccion(
                requestDto = TransaccionRequestDto(
                    transaccionId = _uiState.value.transaccionId ?: 0,
                    categoriaId = _uiState.value.categoriaId ?: 0,
                    usuarioId = authRepo.getUser()?.usuarioId ?: 0,
                    monto = _uiState.value.monto ?: 0.0,
                    usarSugerencia = _uiState.value.usarEnSugerencia == true,
                    descripcion = _uiState.value.descripcionTransaccion ?: "",
                )
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorGeneral = result.message
                            )
                        }

                    is Resource.Loading -> _uiState.update {
                        it.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Success -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorGeneral = ""
                        )
                    }
                }
            }
        }
    }

    private fun onDelete(transaccion: TransaccionEntity) {
        viewModelScope.launch {
            var seEliminoTransaccion = false
            transRepo.eliminarTransaccion(
                transaccionId = transaccion.transaccionId ?: 0
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorGeneral = result.message
                        )
                    }

                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }

                    is Resource.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        seEliminoTransaccion = result.data == true
                    }
                }
            }
            if (seEliminoTransaccion)
                getTransacciones(
                    tipoCategoria = catRepo
                        .findCategoria(transaccion.categoriaId)?.tipo
                        ?: "GASTOS"
                )
        }
    }

    private fun onUsarEnSugerenciaChange(usarEnSugerencia: Boolean) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    usarEnSugerencia = usarEnSugerencia,
                )
            }
        }
    }

    private fun onMontoChange(monto: Double) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    monto = monto,
                    errorMonto = ""
                )
            }
        }
    }

    private fun onDescripcionTransaccionChange(descripcion: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    descripcionTransaccion = descripcion,
                    errorDescripcionTransaccion = ""
                )
            }
        }
    }

    private fun onCategoriaChage(categoriaId: Long?, descripcion: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    categoriaId = categoriaId,
                    descripcionCategoria = descripcion,
                    errorCategoria = ""
                )
            }
        }
    }

    private fun onLimpiar() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    errorCategoria = "",
                    errorDescripcionTransaccion = "",
                    errorMonto = "",
                    errorGeneral = "",
                )
            }
        }
    }
}
