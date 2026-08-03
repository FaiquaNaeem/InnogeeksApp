package com.example.innogeeks.feature_home.presentation.home.components

import android.content.res.Configuration
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.innogeeks.feature_home.domain.model.DomainPreview
import com.example.innogeeks.ui.theme.InnogeeksTheme
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.pow
import kotlin.math.sin

private const val WEDGE_COUNT = 6
private const val WEDGE_SWEEP = 360f / WEDGE_COUNT
private const val IDLE_SPEED_DEG_PER_SEC = 4f
private const val SNAP_DURATION_MS = 900f
private const val HOLD_DURATION_MS = 1300f

private enum class WheelMode { IDLE, SNAP, HOLD }

@Composable
fun DomainWheel(
    domains: List<DomainPreview>,
    selectedId: String?,
    onDomainSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (domains.isEmpty()) return

    val textMeasurer = rememberTextMeasurer()

    var rotation by remember { mutableFloatStateOf(0f) }
    var mode by remember { mutableStateOf(WheelMode.IDLE) }
    var snapFrom by remember { mutableFloatStateOf(0f) }
    var snapTo by remember { mutableFloatStateOf(0f) }
    var snapElapsed by remember { mutableFloatStateOf(0f) }
    var holdElapsed by remember { mutableFloatStateOf(0f) }

    // Rotate the selected wedge's midpoint to the top of the wheel, taking the shortest route.
    LaunchedEffect(selectedId, domains) {
        val index = domains.indexOfFirst { it.id == selectedId }
        if (index < 0) return@LaunchedEffect
        val midAngle = index * WEDGE_SWEEP + WEDGE_SWEEP / 2f
        val desired = ((-midAngle % 360f) + 360f) % 360f
        val turns = ((rotation - desired) / 360f).let { Math.round(it) }
        snapFrom = rotation
        snapTo = desired + turns * 360f
        snapElapsed = 0f
        mode = WheelMode.SNAP
    }

    // Single frame loop driving idle spin, the snap ease-out, and the post-snap hold.
    LaunchedEffect(Unit) {
        var lastFrame = 0L
        while (true) {
            withFrameNanos { now ->
                if (lastFrame == 0L) lastFrame = now
                val dtMs = (now - lastFrame) / 1_000_000f
                lastFrame = now

                when (mode) {
                    WheelMode.IDLE -> rotation += IDLE_SPEED_DEG_PER_SEC * (dtMs / 1000f)

                    WheelMode.SNAP -> {
                        snapElapsed += dtMs
                        val t = (snapElapsed / SNAP_DURATION_MS).coerceAtMost(1f)
                        val eased = 1f - (1f - t).pow(3)
                        rotation = snapFrom + (snapTo - snapFrom) * eased
                        if (t >= 1f) {
                            mode = WheelMode.HOLD
                            holdElapsed = 0f
                        }
                    }

                    WheelMode.HOLD -> {
                        holdElapsed += dtMs
                        if (holdElapsed >= HOLD_DURATION_MS) mode = WheelMode.IDLE
                    }
                }
            }
        }
    }

    val scheme = MaterialTheme.colorScheme
    val wedgeColors = remember(scheme) {
        listOf(
            scheme.primary,
            scheme.secondary,
            scheme.tertiary,
            scheme.secondaryContainer,
            scheme.primaryContainer,
            scheme.onPrimaryContainer
        )
    }
    val labelColors = remember(scheme) {
        listOf(
            scheme.onPrimary,
            scheme.onSecondary,
            scheme.onTertiary,
            scheme.onSecondaryContainer,
            scheme.onPrimaryContainer,
            scheme.primaryContainer
        )
    }
    val strokeColor = scheme.surfaceContainerLowest

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        androidx.compose.foundation.Canvas(
            modifier = Modifier
                .size(152.dp)
                .pointerInput(domains) {
                    detectTapGestures { tap ->
                        val centre = Offset(size.width / 2f, size.height / 2f)
                        val dx = tap.x - centre.x
                        val dy = tap.y - centre.y
                        // Ignore taps outside the disc.
                        if (hypot(dx, dy) > size.width / 2f) return@detectTapGestures

                        // atan2 gives the tap's angle; subtracting rotation maps it back to a fixed wedge.
                        val tapAngle = Math.toDegrees(atan2(dy, dx).toDouble()).toFloat()
                        val unrotated = (((tapAngle - rotation) % 360f) + 360f) % 360f
                        val index = (unrotated / WEDGE_SWEEP).toInt().coerceIn(0, domains.lastIndex)
                        onDomainSelected(domains[index].id)
                    }
                }
        ) {
            val radius = size.minDimension / 2f
            val centre = Offset(size.width / 2f, size.height / 2f)

            domains.forEachIndexed { index, domain ->
                val startAngle = index * WEDGE_SWEEP + rotation
                val isSelected = domain.id == selectedId
                val fill = wedgeColors[index % wedgeColors.size]

                drawArc(
                    color = fill,
                    startAngle = startAngle,
                    sweepAngle = WEDGE_SWEEP,
                    useCenter = true,
                    alpha = if (isSelected) 1f else 0.8f,
                    topLeft = Offset(centre.x - radius, centre.y - radius),
                    size = Size(radius * 2, radius * 2)
                )
                drawArc(
                    color = strokeColor,
                    startAngle = startAngle,
                    sweepAngle = WEDGE_SWEEP,
                    useCenter = true,
                    style = Stroke(width = if (isSelected) 3.dp.toPx() else 1.5.dp.toPx()),
                    topLeft = Offset(centre.x - radius, centre.y - radius),
                    size = Size(radius * 2, radius * 2)
                )
            }

            // Labels are drawn after the wedges and counter-rotated so they stay upright.
            domains.forEachIndexed { index, domain ->
                val midAngle = index * WEDGE_SWEEP + WEDGE_SWEEP / 2f + rotation
                val midRad = Math.toRadians(midAngle.toDouble())
                val labelRadius = radius * 0.62f
                val labelCentre = Offset(
                    centre.x + (cos(midRad) * labelRadius).toFloat(),
                    centre.y + (sin(midRad) * labelRadius).toFloat()
                )

                val measured = textMeasurer.measure(
                    text = domain.wheelLabel,
                    style = TextStyle(
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = labelColors[index % labelColors.size]
                    )
                )

                rotate(degrees = -rotation, pivot = labelCentre) {
                    drawText(
                        textLayoutResult = measured,
                        topLeft = Offset(
                            labelCentre.x - measured.size.width / 2f,
                            labelCentre.y - measured.size.height / 2f
                        )
                    )
                }
            }

            // Hub disc.
            drawCircle(
                color = strokeColor,
                radius = radius * 0.22f,
                center = centre
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.08f),
                radius = radius * 0.22f,
                center = centre,
                style = Stroke(width = 1.5.dp.toPx())
            )
        }

        val selected = domains.firstOrNull { it.id == selectedId } ?: domains.first()
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = selected.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = selected.blurb,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DomainWheelPreview() {
    InnogeeksTheme {
        Box(modifier = Modifier.padding(vertical = 16.dp)) {
            DomainWheel(
                domains = previewDomains,
                selectedId = "webd",
                onDomainSelected = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DomainWheelSecondSelectionPreview() {
    InnogeeksTheme {
        Box(modifier = Modifier.padding(vertical = 16.dp)) {
            DomainWheel(
                domains = previewDomains,
                selectedId = "arvr",
                onDomainSelected = {}
            )
        }
    }
}

internal val previewDomains = listOf(
    DomainPreview("webd", "Web Dev", "WEB D", "Full-stack crews building the club's own platforms."),
    DomainPreview("appd", "App Dev", "APP D", "Native & cross-platform builders shipping Android and iOS apps."),
    DomainPreview("ml", "Machine Learning", "ML", "Model-training practitioners chasing leaderboard ranks."),
    DomainPreview("arvr", "AR / VR", "AR VR", "Immersive tinkerers building spatial experiences with Unity & WebXR."),
    DomainPreview("blockchain", "Blockchain", "CHAIN", "Smart contracts, chains, and Web3 tooling explorers."),
    DomainPreview("iot", "IoT", "IOT", "Hardware and firmware hackers wiring sensors to the real world.")
)
