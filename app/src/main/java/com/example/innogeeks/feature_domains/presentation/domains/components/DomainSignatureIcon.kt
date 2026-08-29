package com.example.innogeeks.feature_domains.presentation.domains.components

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.innogeeks.ui.theme.InnogeeksTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

// Each domain gets its own animated glyph, drawn on a shared 68.dp stage.
@Composable
fun DomainSignatureIcon(
    domainId: String,
    accent: Color,
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition(label = "signature")
    // Single 0..1 driver reused by every glyph so they all share one clock.
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "signatureProgress"
    )

    val muted = MaterialTheme.colorScheme.outlineVariant

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(68.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .border(1.dp, muted, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(10.dp)) {
            when (domainId) {
                "webd" -> drawBrowserTyping(progress, accent, muted)
                "appd" -> drawAppGrid(progress, accent, muted)
                "ml" -> drawNeuralNet(progress, accent, muted)
                "arvr" -> drawSpinningCube(progress, accent, muted)
                "blockchain" -> drawChainLinks(progress, accent, muted)
                else -> drawIotBeams(progress, accent, muted)
            }
        }
    }
}

// Browser chrome with three code lines that type out in sequence.
private fun DrawScope.drawBrowserTyping(progress: Float, accent: Color, muted: Color) {
    val w = size.width
    val h = size.height
    val frameHeight = h
    drawRoundRect(
        color = muted,
        size = Size(w, frameHeight),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f),
        style = Stroke(width = 2f)
    )

    val barY = frameHeight * 0.22f
    drawLine(muted, Offset(0f, barY), Offset(w, barY), strokeWidth = 1.5f)
    repeat(3) { index ->
        drawCircle(
            color = accent.copy(alpha = 0.6f),
            radius = 2.5f,
            center = Offset(8f + index * 9f, barY / 2f)
        )
    }

    val lineWidths = listOf(0.66f, 0.44f, 0.55f)
    lineWidths.forEachIndexed { index, fraction ->
        // Each line owns a third of the cycle, so they appear to type one after another.
        val slotStart = index / 3f
        val local = ((progress - slotStart) * 3f).coerceIn(0f, 1f)
        val y = barY + 12f + index * 12f
        drawLine(
            color = accent,
            start = Offset(10f, y),
            end = Offset(10f + (w - 20f) * fraction * local, y),
            strokeWidth = 3f
        )
    }
}

// Phone outline whose nine app tiles pop in one by one.
private fun DrawScope.drawAppGrid(progress: Float, accent: Color, muted: Color) {
    val phoneWidth = size.height * 0.55f
    val left = (size.width - phoneWidth) / 2f
    drawRoundRect(
        color = muted,
        topLeft = Offset(left, 0f),
        size = Size(phoneWidth, size.height),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f),
        style = Stroke(width = 2f)
    )

    val cell = phoneWidth / 5f
    val gridLeft = left + cell * 0.7f
    val gridTop = size.height * 0.18f
    repeat(9) { index ->
        val slotStart = index / 9f
        val local = ((progress - slotStart) * 9f).coerceIn(0f, 1f)
        val row = index / 3
        val col = index % 3
        val tile = cell * local
        drawRoundRect(
            color = accent.copy(alpha = local),
            topLeft = Offset(
                gridLeft + col * cell * 1.2f + (cell - tile) / 2f,
                gridTop + row * cell * 1.2f + (cell - tile) / 2f
            ),
            size = Size(tile, tile),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(2f, 2f)
        )
    }
}

// Three-layer network whose edges pulse as the signal travels left to right.
private fun DrawScope.drawNeuralNet(progress: Float, accent: Color, muted: Color) {
    val layers = listOf(3, 4, 2)
    val columnGap = size.width / (layers.size + 1)
    val positions = layers.mapIndexed { layerIndex, count ->
        val x = columnGap * (layerIndex + 1)
        val rowGap = size.height / (count + 1)
        List(count) { Offset(x, rowGap * (it + 1)) }
    }

    positions.zipWithNext().forEachIndexed { edgeLayer, (from, to) ->
        val slotStart = edgeLayer / 2f
        val local = ((progress - slotStart) * 2f).coerceIn(0f, 1f)
        from.forEach { a ->
            to.forEach { b ->
                drawLine(muted, a, b, strokeWidth = 1f)
                drawLine(
                    color = accent,
                    start = a,
                    end = Offset(a.x + (b.x - a.x) * local, a.y + (b.y - a.y) * local),
                    strokeWidth = 1.6f
                )
            }
        }
    }

    positions.flatten().forEach { drawCircle(accent, radius = 3.5f, center = it) }
}

// Wireframe cube whose front face sweeps horizontally to fake a Y-axis spin.
private fun DrawScope.drawSpinningCube(progress: Float, accent: Color, muted: Color) {
    val side = size.height * 0.6f
    val centre = Offset(size.width / 2f, size.height / 2f)
    val angle = progress * 2f * PI.toFloat()
    val skew = cos(angle) * side * 0.28f
    val depth = side * 0.3f

    val backTopLeft = Offset(centre.x - side / 2f + skew, centre.y - side / 2f - depth * 0.4f)
    val frontTopLeft = Offset(centre.x - side / 2f - skew, centre.y - side / 2f + depth * 0.4f)

    drawRect(
        color = muted,
        topLeft = backTopLeft,
        size = Size(side, side),
        style = Stroke(width = 1.5f)
    )
    drawRect(
        color = accent,
        topLeft = frontTopLeft,
        size = Size(side, side),
        style = Stroke(width = 2f)
    )

    listOf(
        Offset(0f, 0f),
        Offset(side, 0f),
        Offset(0f, side),
        Offset(side, side)
    ).forEach { corner ->
        drawLine(
            color = accent.copy(alpha = 0.5f),
            start = backTopLeft + corner,
            end = frontTopLeft + corner,
            strokeWidth = 1.2f
        )
    }
}

// Four interlocking rings with a highlight that walks the chain.
private fun DrawScope.drawChainLinks(progress: Float, accent: Color, muted: Color) {
    val count = 4
    val radius = size.height * 0.26f
    val spacing = radius * 1.5f
    val startX = size.width / 2f - spacing * (count - 1) / 2f
    val active = (progress * count).toInt().coerceIn(0, count - 1)

    repeat(count) { index ->
        val centre = Offset(startX + index * spacing, size.height / 2f)
        rotate(degrees = if (index % 2 == 0) 0f else 30f, pivot = centre) {
            drawCircle(
                color = if (index == active) accent else muted,
                radius = radius,
                center = centre,
                style = Stroke(width = if (index == active) 3f else 2f)
            )
        }
    }
}

// Sensor node emitting three expanding signal arcs.
private fun DrawScope.drawIotBeams(progress: Float, accent: Color, muted: Color) {
    val centre = Offset(size.width / 2f, size.height * 0.72f)
    drawCircle(accent, radius = 5f, center = centre)
    drawLine(
        color = muted,
        start = centre,
        end = Offset(centre.x, size.height),
        strokeWidth = 2f
    )

    repeat(3) { index ->
        val slotStart = index / 3f
        val local = ((progress - slotStart + 1f) % 1f)
        val radius = size.height * 0.18f + local * size.height * 0.5f
        drawArc(
            color = accent.copy(alpha = (1f - local) * 0.8f),
            startAngle = 200f,
            sweepAngle = 140f,
            useCenter = false,
            topLeft = Offset(centre.x - radius, centre.y - radius),
            size = Size(radius * 2f, radius * 2f),
            style = Stroke(width = 2f)
        )
    }
    // Keeps the import used and the node visually anchored.
    drawCircle(accent.copy(alpha = 0.25f), radius = 5f + sin(progress * PI.toFloat()) * 3f, center = centre)
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DomainSignatureIconPreview() {
    InnogeeksTheme {
        val scheme = MaterialTheme.colorScheme
        val ids = listOf("webd", "appd", "ml", "arvr", "blockchain", "iot")
        val accents = listOf(
            scheme.primary,
            scheme.secondary,
            scheme.tertiary,
            scheme.secondaryContainer,
            scheme.primaryContainer,
            scheme.onPrimaryContainer
        )
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ids.chunked(2).forEachIndexed { rowIndex, pair ->
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    pair.forEachIndexed { colIndex, id ->
                        DomainSignatureIcon(
                            domainId = id,
                            accent = accents[rowIndex * 2 + colIndex],
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
