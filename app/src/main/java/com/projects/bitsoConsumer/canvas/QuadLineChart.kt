package com.projects.bitsoConsumer.canvas

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.bitsoConsumer.models.trading.PayloadTradesGraph
import com.projects.bitsoConsumer.support.round
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun QuadLineChart(

    data: List<PayloadTradesGraph> = emptyList(),
    modifier: Modifier = Modifier,
    color1: Color,
) {
    val spacing = 100f
    val min = data.minOf { it.price.round(2) }
    val max = data.maxOf { it.price.round(2) }
    val range = (max - min)

    val transparentGraphColor = remember { color1.copy(alpha = 0.5f) }
    val upperValue = if (range < 10) {
        remember { max }
    } else {
        remember { (data.maxOfOrNull { it.price.round(0) }?.plus(1))?.roundToInt() ?: 0 }
    }

    val lowerValue = if (range < 10) {
        remember { min }
    } else {
        remember { (data.minOfOrNull { it.price.round(0) }?.toInt() ?: 0) }
    }
    val density = LocalDensity.current

    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Canvas(modifier = modifier) {
        val spacePerHour = (size.width - spacing) / data.size
        (data.indices step 2).forEach { i ->
            val hour = data[i].index
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    hour.toString(),
                    (spacing + i * spacePerHour) + 70F,
                    size.height,
                    textPaint,
                )
            }
        }

        val divider = (upperValue.toDouble() - lowerValue.toDouble())

        val priceStep = (divider) / 5f
        (0..5).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    if (divider < 1) {
                        (lowerValue.toDouble() + priceStep * i).round(3).toString()
                    } else {
                        round(lowerValue.toDouble() + priceStep * i).toString()
                    },
                    90f,
                    size.height - spacing - i * size.height / 5f,
                    textPaint,
                )
            }
        }

        var medX: Float
        var medY: Float

        val strokePath = Path().apply {
            val height = size.height
            data.indices.forEach { i ->
                val nextInfo = data.getOrNull(i + 1) ?: data.last()
                val firstRatio = (data[i].price - lowerValue.toDouble()) / (divider)
                val secondRatio = (nextInfo.price - lowerValue.toDouble()) / (divider)

                val x1 = (spacing + i * spacePerHour) + 70F
                val y1 = height - spacing - (firstRatio * height).toFloat()
                val x2 = (spacing + (i + 1) * spacePerHour) + 70F
                val y2 = height - spacing - (secondRatio * height).toFloat()
                if (i == 0) {
                    moveTo(x1, y1)
                } else {
                    medX = (x1 + x2) / 2f
                    medY = (y1 + y2) / 2f
                    quadraticBezierTo(x1 = x1, y1 = y1, x2 = medX, y2 = medY)
                }
            }
        }

        drawPath(
            path = strokePath,
            color = color1,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round,
            ),
        )

        val fillPath = android.graphics.Path(strokePath.asAndroidPath()).asComposePath().apply {
            lineTo(size.width - spacePerHour + 70F, size.height - spacing)
            lineTo(spacing + 70f, size.height - spacing)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    transparentGraphColor,
                    Color.Transparent,
                ),
                endY = size.height - spacing,
            ),
        )
    }
}
