package com.cashcontrol.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine

@Composable
fun GraficoCurvas() {
    val pointsData: List<Point> =
        listOf(Point(0f, 40f), Point(1f, 90f), Point(2f, 0f), Point(3f, 60f), Point(4f, 10f))

    val xAxisData = AxisData.Builder()
        .axisLineColor(MaterialTheme.colorScheme.secondary)
        .axisLabelColor(MaterialTheme.colorScheme.secondary)
        .axisStepSize(100.dp)
        .backgroundColor(MaterialTheme.colorScheme.surface)
        .steps(pointsData.size - 1)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .axisLineColor(MaterialTheme.colorScheme.secondary)
        .axisLabelColor(MaterialTheme.colorScheme.secondary)
        .steps(pointsData.size - 1)
        .backgroundColor(MaterialTheme.colorScheme.surface)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val yScale = 100f / (pointsData.size - 1)
            String.format("%.1f", i * yScale)
        }.build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    IntersectionPoint(
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(
            enableVerticalLines = false,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.secondary),
            color = MaterialTheme.colorScheme.primary,

            ),
        backgroundColor = MaterialTheme.colorScheme.surface
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Gráfico de ingresos",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.outline,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Start
            )
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                lineChartData = lineChartData
            )
        }
    }
}

@Preview
@Composable
fun PreviewGraficoCurvas() {
    GraficoCurvas()
}