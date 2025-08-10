package com.cashcontrol.presentation.transaccion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableIntStateOf
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
import com.cashcontrol.data.local.entities.TransaccionEntity
import com.cashcontrol.presentation.composables.CashControlAppBar
import com.cashcontrol.presentation.composables.PillButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransaccionListScreen(
    viewModel: TransaccionViewModel = hiltViewModel(),
    goToTransaccion: (Long?) -> Unit,
    goBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ListadoTransaccion(
        uiState = uiState,
        goBack = goBack,
        goToTransaccion = { goToTransaccion(it) },
        onDeleteClick = { viewModel.onEvent(TransaccionEvent.Delete(it)) },
        verTiposCategoria = { viewModel.onEvent(TransaccionEvent.GetTransacciones(it)) },
        onUsarEnSugerenciaClick = { viewModel.onEvent(TransaccionEvent.UsarEnSugerenciaChange(it)) },
    )
}

@Composable
fun ListadoTransaccion(
    uiState: TransaccionUiState,
    goBack: () -> Unit,
    goToTransaccion: (Long?) -> Unit,
    onDeleteClick: (TransaccionEntity) -> Unit,
    onUsarEnSugerenciaClick: (Boolean) -> Unit,
    verTiposCategoria: (String) -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceDim,
        topBar = {
            CashControlAppBar(
                icono = Icons.AutoMirrored.Filled.ArrowBack,
                goBack = { goBack() },
                titulo = stringResource(R.string.lista_transaccion),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    goToTransaccion(0)
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
            var seleccionActual by remember { mutableIntStateOf(0) }

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
                            verTiposCategoria(texto.uppercase())
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
                    items(uiState.listaTransacciones) { transaccion ->
                        var descripcionCategoria = uiState.listaCategoriasFiltradas.find {
                            it.categoriaId == transaccion.categoriaId
                        }?.descripcion ?: "N/D"

                        var tipoCategoria = uiState.listaCategoriasFiltradas.find {
                            it.categoriaId == transaccion.categoriaId
                        }?.tipo ?: "INGRESOS"

                        var dateFormatter = remember {
                            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        }

                        // Texto formateado
                        var fechaFormateada = remember(transaccion.fechaTransaccion) {
                            dateFormatter.format(transaccion.fechaTransaccion)
                        }

                        TransaccionCard(
                            monto = transaccion.monto,
                            desripcionCategoria = descripcionCategoria,
                            tipoCategoria = tipoCategoria,
                            descripcionTransaccion = transaccion.descripcion,
                            fecha = transaccion.fechaTransaccion.toString(),
                            estadoInicialUsoEnSugerencia = transaccion.usarSugerencia,
                            onSuggestionChange = { onUsarEnSugerenciaClick(transaccion.usarSugerencia) },
                            onEdit = { goToTransaccion(transaccion.transaccionId) },
                            onDelete = { onDeleteClick(transaccion) },
                            transaccionId = transaccion.transaccionId!!,
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
    ListadoTransaccion(
        uiState = TransaccionUiState(
            listaTransacciones = listOf(
                TransaccionEntity(
                    transaccionId = 1,
                    usuarioId = 1,
                    categoriaId = 1,
                    monto = 523500.45,
                    fechaTransaccion = Date(),
                    usarSugerencia = false,
                    descripcion = "Pago quincenal"
                ),
                TransaccionEntity(
                    transaccionId = 2,
                    usuarioId = 1,
                    categoriaId = 1,
                    monto = 523500.45,
                    fechaTransaccion = Date(),
                    usarSugerencia = true,
                    descripcion = "Pago quincenal"
                ),
                TransaccionEntity(
                    transaccionId = 3,
                    usuarioId = 1,
                    categoriaId = 2,
                    monto = 14254.45,
                    fechaTransaccion = Date(),
                    usarSugerencia = true,
                    descripcion = "Descripcion"
                ),
            ),
            listaCategoriasFiltradas = listOf(
                CategoriaEntity(
                    categoriaId = 1,
                    usuarioId = 1,
                    tipo = "GASTOS",
                    descripcion = "Prueba gasto"
                ),
                CategoriaEntity(
                    categoriaId = 2,
                    usuarioId = 1,
                    tipo = "INGRESOS",
                    descripcion = "Prueba ingreso"
                ),
            )
        ),
        goToTransaccion = {},
        goBack = {},
        onDeleteClick = {},
        verTiposCategoria = {},
        onUsarEnSugerenciaClick = {},
    )
}