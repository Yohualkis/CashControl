package com.cashcontrol.presentation.transaccion

import com.cashcontrol.data.local.entities.CategoriaEntity
import com.cashcontrol.data.local.entities.TransaccionEntity
import java.util.Date

data class TransaccionUiState(
    val transaccionId: Long? = 0,
    val categoriaId: Long? = 0,
    val tipoCategoria: String? = "",
    val descripcionTransaccion: String? = "",
    val descripcionCategoria: String? = "",
    val fechaTransaccion: Date? = null,
    val monto: Double? = 0.0,
    val usarEnSugerencia: Boolean? = null,
    val errorCategoria: String? = "",
    val errorDescripcionTransaccion: String? = "",
    val errorMonto: String? = "",
    val errorGeneral: String? = "",
    val isLoading: Boolean = false,
    val listaTransacciones: List<TransaccionEntity> = emptyList(),
    val listaCategoriasFiltradas: List<CategoriaEntity> = emptyList(),
    val listaAllCategorias: List<CategoriaEntity> = emptyList(),
)
