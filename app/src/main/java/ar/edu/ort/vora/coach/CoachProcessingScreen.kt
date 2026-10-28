
package ar.edu.ort.vora.coach

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ProcessingBackground = Color(0xFF103F35)
private val ProcessingCard = Color(0xFF204D43)
private val ProcessingMint = Color(0xFF62D8B5)
private val ProcessingMuted = Color(0xFFA6C2B8)

@Composable
fun CoachProcessingScreen(
    transcript: String = "I had chicken, rice and some vegetables for lunch.",
    onCancel: () -> Unit = {},
    onFinishProcessing: () -> Unit = {}
) {
    Scaffold(
        containerColor = ProcessingBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ProcessingBackground)
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Encabezado
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .background(ProcessingCard, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "‹",
                        color = Color.White,
                        fontSize = 22.sp
                    )
                }

                Text(
                    text = "Processing",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 9.dp),
                    color = Color.White,
                    fontSize = 12.sp
                )
            }

            Spacer(Modifier.height(62.dp))

            // Esfera más pequeña durante el procesamiento
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        color = Color(0xFFBDEB70),
                        shape = CircleShape
                    )
                    .padding(5.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF8BE4BE),
                                Color(0xFF2A8E72),
                                ProcessingBackground
                            )
                        ),
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = ProcessingMint,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                ProcessingWaveform(
                    modifier = Modifier
                        .width(28.dp)
                        .height(24.dp)
                )
            }

            Spacer(Modifier.height(32.dp))

            Text(
                text = "THINKING...",
                color = ProcessingMint,
                fontSize = 10.sp
            )

            Spacer(Modifier.height(14.dp))

            // Texto que VORA reconoció
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = ProcessingCard,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFF35675A),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .padding(14.dp)
            ) {
                Text(
                    text = "\"$transcript\"",
                    color = ProcessingMuted,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )
            }

            Spacer(Modifier.height(25.dp))

            // Indicador visual de procesamiento
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ProcessingIndicator()

                Spacer(Modifier.width(8.dp))

                Text(
                    text = "Checking your nutrition target...",
                    color = ProcessingMuted,
                    fontSize = 10.sp
                )
            }

            Spacer(Modifier.weight(1f))

            TextButton(
                onClick = onCancel
            ) {
                Text(
                    text = "Cancel",
                    color = ProcessingMuted,
                    fontSize = 11.sp
                )
            }

            // Permite avanzar en la demo sin esperar un servicio real
            TextButton(
                onClick = onFinishProcessing
            ) {
                Text(
                    text = "Continue demo",
                    color = ProcessingMint,
                    fontSize = 10.sp
                )
            }

            Spacer(Modifier.height(4.dp))
        }
    }
}

@Composable
private fun ProcessingWaveform(
    modifier: Modifier = Modifier
) {
    val heights = listOf(7, 12, 17, 10, 21, 13, 18, 9, 14)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(1.5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        heights.forEach { height ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(height.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(50)
                    )
            )
        }
    }
}

@Composable
private fun ProcessingIndicator() {
    Canvas(
        modifier = Modifier.size(13.dp)
    ) {
        drawArc(
            color = ProcessingMint,
            startAngle = 40f,
            sweepAngle = 285f,
            useCenter = false,
            style = androidx.compose.ui.graphics.drawscope.Stroke(
                width = 2.dp.toPx()
            )
        )
    }
}
