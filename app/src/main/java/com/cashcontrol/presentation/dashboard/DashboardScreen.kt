package com.cashcontrol.presentation.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cashcontrol.R
import com.cashcontrol.presentation.composables.CashControlAppBar
import com.cashcontrol.presentation.composables.CashControlCardSugerencia
import com.cashcontrol.presentation.composables.GraficoCurvas
import com.cashcontrol.presentation.composables.GraficoDona
import com.cashcontrol.presentation.composables.PillButton
import com.cashcontrol.presentation.navigation.Screen

@Composable
fun DashboardScreen(
    goToDashboard: () -> Unit,
    goToSugerencias: () -> Unit,
) {
    DashboardScreenView(
        goToDashboard = goToDashboard,
        goToSugerencias = goToSugerencias,
    )
}

@Composable
fun DashboardScreenView(
    goToDashboard: () -> Unit,
    goToSugerencias: () -> Unit,
) {
    var estadoInicialSeleccion1 = true
    var estadoInicialSeleccion2 = false
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            CashControlAppBar(
                icono = Icons.Default.Menu,
                goBack = { },
                titulo = stringResource(R.string.app_name_),
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = MaterialTheme.colorScheme.surfaceDim)
                .verticalScroll(rememberScrollState())
        ) {
            // DASHBORAD TABS
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                PillButton(
                    texto = Screen.Dashboard.toString(),
                    estadoInicialSeleccion = estadoInicialSeleccion1,
                    onClick = { goToDashboard }
                )
                PillButton(
                    texto = Screen.Sugerencias.toString(),
                    estadoInicialSeleccion = estadoInicialSeleccion2,
                    onClick = { goToSugerencias }
                )
            }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    count = 3
                ) { item ->
                    // A ESTE CARD HAY Q PASARLE EL MES (FECHA), MONTO TOTAL INGRESOS
                    // Y MONTO TOTAL DE GASTOS
                    CashControlCardSugerencia()
                }
            }
            Spacer(Modifier.height(16.dp))

            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                GraficoCurvas()
            }

            Spacer(Modifier.height(16.dp))

            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                GraficoDona()
            }

        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun DashboardScreenPreview() {
    DashboardScreenView(
        goToDashboard = {},
        goToSugerencias = {},
    )
}