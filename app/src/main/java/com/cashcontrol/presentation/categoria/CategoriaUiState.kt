package com.cashcontrol.presentation.categoria

import com.cashcontrol.data.local.entities.CategoriaEntity

data class CategoriaUiState(
    val categoriaId: Long? = 0,
    val descripcion: String? = "",
    val tipoCategoria: String? = "",
    val errorDescripcion: String? = "",
    val errorTipoCategoria: String? = "",
    val errorGeneral: String? = "",
    val isLoading: Boolean = false,
    val listaCategoria: List<CategoriaEntity> = emptyList(),
)
