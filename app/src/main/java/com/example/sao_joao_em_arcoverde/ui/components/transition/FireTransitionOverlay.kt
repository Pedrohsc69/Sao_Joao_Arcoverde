package com.example.sao_joao_em_arcoverde.ui.components.transition

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.sao_joao_em_arcoverde.ui.theme.LocalAppColors
import kotlinx.coroutines.delay

@Composable
fun FireTransitionOverlay(
    visible: Boolean,
    onAnimationFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    val progress by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(
            durationMillis = FIRE_TRANSITION_DURATION,
            easing = FastOutSlowInEasing
        ),
        label = "fireTransitionProgress"
    )

    LaunchedEffect(visible) {
        if (visible) {
            delay(FIRE_TRANSITION_DURATION.toLong())
            onAnimationFinished()
        }
    }

    if (visible || progress > 0f) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    Color.Black.copy(alpha = progress * 0.42f)
                )
        ) {
            FireCanvas(
                progress = progress,
                modifier = Modifier.fillMaxSize()
            )

            Icon(
                imageVector = Icons.Rounded.LocalFireDepartment,
                contentDescription = null,
                tint = appColors.primary.copy(alpha = progress),
                modifier = Modifier
                    .align(Alignment.Center)
                    .graphicsLayer {
                        alpha = progress
                        scaleX = 0.8f + progress * 1.2f
                        scaleY = 0.8f + progress * 1.2f
                    }
            )
        }
    }
}

@Composable
private fun FireCanvas(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Canvas(
        modifier = modifier
    ) {
        val width = size.width
        val height = size.height

        val fireTop = height * (1f - progress * 1.18f)
        val baseY = height + 80f

        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    appColors.red.copy(alpha = 0.12f * progress),
                    appColors.primary.copy(alpha = 0.22f * progress),
                    Color.Black.copy(alpha = 0.78f * progress)
                ),
                startY = fireTop,
                endY = height
            ),
            topLeft = Offset(0f, fireTop.coerceAtLeast(0f))
        )

        val flameCount = 11

        repeat(flameCount) { index ->
            val normalized = index / (flameCount - 1f)
            val centerX = width * normalized
            val flameHeight = height * (0.34f + ((index % 4) * 0.045f))
            val flameWidth = width * 0.18f

            val offsetWave = kotlin.math.sin(
                (progress * 5f + index) * 1.6f
            ) * 22f

            val x = centerX + offsetWave
            val topY = baseY - flameHeight - (progress * height * 0.70f)

            rotate(
                degrees = ((index % 3) - 1) * 5f,
                pivot = Offset(x, baseY)
            ) {
                drawFlamePath(
                    centerX = x,
                    baseY = baseY,
                    topY = topY,
                    flameWidth = flameWidth,
                    outerColor = appColors.red.copy(alpha = 0.92f * progress),
                    middleColor = Color(0xFFFF7A18).copy(alpha = 0.94f * progress),
                    innerColor = appColors.primary.copy(alpha = 0.96f * progress)
                )
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawFlamePath(
    centerX: Float,
    baseY: Float,
    topY: Float,
    flameWidth: Float,
    outerColor: Color,
    middleColor: Color,
    innerColor: Color
) {
    val outerPath = Path().apply {
        moveTo(centerX, topY)
        cubicTo(
            centerX - flameWidth * 0.95f,
            topY + 120f,
            centerX - flameWidth * 0.95f,
            baseY - 100f,
            centerX,
            baseY
        )
        cubicTo(
            centerX + flameWidth * 0.95f,
            baseY - 100f,
            centerX + flameWidth * 0.95f,
            topY + 120f,
            centerX,
            topY
        )
        close()
    }

    drawPath(
        path = outerPath,
        brush = Brush.verticalGradient(
            colors = listOf(
                innerColor,
                middleColor,
                outerColor
            ),
            startY = topY,
            endY = baseY
        )
    )

    val innerTop = topY + 95f
    val innerBase = baseY - 22f
    val innerWidth = flameWidth * 0.46f

    val innerPath = Path().apply {
        moveTo(centerX, innerTop)
        cubicTo(
            centerX - innerWidth,
            innerTop + 80f,
            centerX - innerWidth,
            innerBase - 70f,
            centerX,
            innerBase
        )
        cubicTo(
            centerX + innerWidth,
            innerBase - 70f,
            centerX + innerWidth,
            innerTop + 80f,
            centerX,
            innerTop
        )
        close()
    }

    drawPath(
        path = innerPath,
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.75f),
                innerColor,
                Color(0xFFFF8A00)
            ),
            startY = innerTop,
            endY = innerBase
        )
    )
}

private const val FIRE_TRANSITION_DURATION = 1200