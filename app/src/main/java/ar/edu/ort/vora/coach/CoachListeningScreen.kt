
package ar.edu.ort.vora.coach

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ListeningBackground = Color(0xFF103F35)
private val ListeningCard = Color(0xFF204D43)
private val ListeningMint = Color(0xFF62D8B5)
private val ListeningMuted = Color(0xFFA6C2B8)
private val ListeningBorder = Color(0xFF35675A)

@Composable
fun CoachListeningScreen(
    onCancel: () -> Unit = {},
    onFinishListening: () -> Unit = {}
) {
    Scaffold(
        containerColor = ListeningBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ListeningBackground)
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
                        .background(
                            color = ListeningCard,
                            shape = CircleShape
                        )
                        .clickable { onCancel() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "‹",
                        color = Color.White,
                        fontSize = 22.sp
                    )
                }

                Text(
                    text = "Listening",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 9.dp),
                    color = Color.White,
                    fontSize = 12.sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(
                                color = ListeningMint,
                                shape = CircleShape
                            )
                    )

                    Text(
                        text = "LIVE AUDIO",
                        color = ListeningMint,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(Modifier.height(35.dp))

            // Esfera de VORA con onda de audio
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .border(
                        width = 2.dp,
                        color = Color(0xFF286B58),
                        shape = CircleShape
                    )
                    .padding(12.dp)
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
                                Color(0xFF103F35)
                            )
                        ),
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = ListeningMint,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                AudioWaveformListening(
                    modifier = Modifier
                        .width(38.dp)
                        .height(30.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

            // Transcripción
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(17.dp),
                colors = CardDefaults.cardColors(
                    containerColor = ListeningCard
                ),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = ListeningBorder
                )
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    Text(
                        text = "LIVE TRANSCRIPT",
                        color = ListeningMint,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = "\"I had chicken, rice and some vegetables for lunch...\"",
                        color = Color.White,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(Modifier.height(26.dp))

            Text(
                text = "Speak naturally. I'm listening.",
                color = ListeningMuted,
                fontSize = 10.sp
            )

            Spacer(Modifier.weight(1f))

            // Botón del micrófono
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .background(
                        color = ListeningMint,
                        shape = CircleShape
                    )
                    .clickable { onFinishListening() },
                contentAlignment = Alignment.Center
            ) {
                MicrophoneListeningIcon(
                    modifier = Modifier.size(26.dp),
                    color = ListeningBackground
                )
            }

            Spacer(Modifier.height(12.dp))

            TextButton(
                onClick = onCancel
            ) {
                Text(
                    text = "Cancel",
                    color = ListeningMint,
                    fontSize = 11.sp
                )
            }

            Spacer(Modifier.height(4.dp))
        }
    }
}

@Composable
private fun AudioWaveformListening(
    modifier: Modifier = Modifier
) {
    val heights = listOf(
        8, 13, 18, 11, 25, 15, 21, 12, 17, 8
    )

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(1.5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        heights.forEach { barHeight ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(barHeight.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(50)
                    )
            )
        }
    }
}

@Composable
private fun MicrophoneListeningIcon(
    modifier: Modifier = Modifier,
    color: Color = Color.White
) {
    Canvas(modifier = modifier) {
        val centerX = size.width / 2f

        // Cabeza del micrófono
        drawRoundRect(
            color = color,
            topLeft = Offset(
                centerX - size.width * 0.16f,
                size.height * 0.08f
            ),
            size = Size(
                size.width * 0.32f,
                size.height * 0.52f
            ),
            cornerRadius = CornerRadius(size.width * 0.18f)
        )

        // Arco exterior
        drawArc(
            color = color,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(
                centerX - size.width * 0.30f,
                size.height * 0.27f
            ),
            size = Size(
                size.width * 0.60f,
                size.height * 0.48f
            ),
            style = Stroke(width = size.width * 0.075f)
        )

        // Soporte vertical
        drawLine(
            color = color,
            start = Offset(
                centerX,
                size.height * 0.76f
            ),
            end = Offset(
                centerX,
                size.height * 0.91f
            ),
            strokeWidth = size.width * 0.075f
        )

        // Base
        drawLine(
            color = color,
            start = Offset(
                centerX - size.width * 0.18f,
                size.height * 0.92f
            ),
            end = Offset(
                centerX + size.width * 0.18f,
                size.height * 0.92f
            ),
            strokeWidth = size.width * 0.075f
        )
    }
}
