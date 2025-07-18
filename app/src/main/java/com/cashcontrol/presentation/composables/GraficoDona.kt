package com.cashcontrol.presentation.composables

import android.graphics.Typeface
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.AccessibilityConfig
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun GraficoDona() {
    val donutData = PieChartData(
        slices = listOf(
            PieChartData.Slice("Música", 28f, Color(0xFFFF6B6B)),
            PieChartData.Slice("Películas", 14f, Color(0xFF4ECDC4)),
            PieChartData.Slice("Libros", 45f, Color(0xFFFFD166)),
            PieChartData.Slice("Juegos", 66f, Color(0xFF06D6A0)),
            PieChartData.Slice("Otros", 9f, Color(0xFF9D79BC))
        ),
        plotType = PlotType.Donut
    )

    val chartConfig = PieChartConfig(
        backgroundColor = MaterialTheme.colorScheme.surface,
        sliceLabelTextColor = MaterialTheme.colorScheme.outline,
        isAnimationEnable = true,
        animationDuration = 1500,
        chartPadding = 25,
        labelVisible = true,
        // Esto puede ser una opcion para que el user selccione si quiere
        // ver porcentajes o valores
        labelType = PieChartConfig.LabelType.PERCENTAGE,
        labelColor = MaterialTheme.colorScheme.outline,
        labelFontSize = 20.sp,
        labelTypeface = Typeface.DEFAULT_BOLD,
        isSumVisible = true,
        accessibilityConfig = AccessibilityConfig(
            chartDescription = "Gráfico de donut mostrando distribución de categorías",
            popUpTopRightButtonTitle = "Cerrar",
            dividerColor = MaterialTheme.colorScheme.outline
        ),
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Gráfico de ingresos",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.outline,
                    fontWeight = FontWeight.Bold
                )
            )
            DonutPieChart(
                modifier = Modifier
                    .size(250.dp)
                    .padding(top = 8.dp),
                pieChartData = donutData,
                pieChartConfig = chartConfig
            )
            Spacer(modifier = Modifier.height(8.dp))

            // LEYENDA
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = 3
            ) {
                donutData.slices.forEach { slice ->
                    LeyendaItem(
                        color = slice.color,
                        label = slice.label,
                        valor = "${slice.value.toInt()}%"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewGraficoDona(){
    GraficoDona()
}