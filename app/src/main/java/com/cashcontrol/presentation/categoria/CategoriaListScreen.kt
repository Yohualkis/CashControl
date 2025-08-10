package com.cashcontrol.presentation.categoria

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cashcontrol.R
import com.cashcontrol.data.local.entities.CategoriaEntity
import com.cashcontrol.presentation.composables.CashControlAppBar
import com.cashcontrol.presentation.composables.PillButton

@Composable
fun CategoriaListScreen(
    viewModel: CategoriaViewModel = hiltViewModel(),
    goToCategoria: (Long?) -> Unit,
    goBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ListadoCategoria(
        uiState = uiState,
        goBack = goBack,
        goToCategoria = { goToCategoria(it) },
        onDeleteClick = { viewModel.onEvent(CategoriaEvent.Delete(it)) },
        verTiposCategoria = { viewModel.getCategoriasPorTipo(it) },
    )
}

@Composable
fun ListadoCategoria(
    uiState: CategoriaUiState,
    goBack: () -> Unit,
    goToCategoria: (Long?) -> Unit,
    onDeleteClick: (CategoriaEntity) -> Unit,
    verTiposCategoria: (String) -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceDim,
        topBar = {
            CashControlAppBar(
                icono = Icons.AutoMirrored.Filled.ArrowBack,
                goBack = { goBack() },
                titulo = stringResource(R.string.lista_categoria),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    goToCategoria(0)
                },
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
            ) {
                Icon(Icons.Filled.Add, "Agregar")
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val opciones = listOf("Gastos", "Ingresos")
            var seleccionActual by remember { mutableStateOf(0) }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                opciones.forEachIndexed { index, texto ->
                    PillButton(
                        texto = texto,
                        estadoInicialSeleccion = index == seleccionActual,
                        onClick = {
                            seleccionActual = index
                            verTiposCategoria(texto)
                        }
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (uiState.isLoading) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 256.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                            Text(
                                text = "Cargando...",
                                color = MaterialTheme.colorScheme.outline,
                            )
                        }
                    }
                } else {
                    items(uiState.listaCategoria) {
                        CategoriaCard(
                            descripcion = it.descripcion,
                            onEditarClick = { goToCategoria(it.categoriaId) },
                            onEliminarClick = { onDeleteClick(it) }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCategoriaListScreen() {
    ListadoCategoria(
        uiState = CategoriaUiState(
            listaCategoria = listOf(
                CategoriaEntity(
                    categoriaId = 1,
                    usuarioId = 1,
                    tipo = "",
                    descripcion = "Ejemplo 1"
                ),
                CategoriaEntity(
                    categoriaId = 2,
                    usuarioId = 1,
                    tipo = "",
                    descripcion = "Ejemplo 2"
                ),
                CategoriaEntity(
                    categoriaId = 3,
                    usuarioId = 1,
                    tipo = "",
                    descripcion = "Ejemplo 3"
                ),
            )
        ),
        goToCategoria = {},
        goBack = {},
        onDeleteClick = {},
        verTiposCategoria = {},
    )
}