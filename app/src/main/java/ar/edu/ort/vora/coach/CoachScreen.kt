
package ar.edu.ort.vora.coach

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val IdleBackground = Color(0xFFF7F8F5)
private val IdleGreen = Color(0xFF103F35)
private val IdleMuted = Color(0xFF858D85)
private val IdleBorder = Color(0xFFE3E8E1)

@Composable
fun CoachScreen(
    onBack: () -> Unit = {},
    onStartVoice: () -> Unit = {},
    onOpenConversation: () -> Unit = {}
) {
    Scaffold(
        containerColor = IdleBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(IdleBackground)
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(27.dp)
                        .background(Color.White, CircleShape)
                        .clickable { onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Text("‹", color = IdleGreen, fontSize = 23.sp)
                }

                Text(
                    text = "VORA Coach",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    color = IdleGreen,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )

                Box(
                    modifier = Modifier
                        .background(
                            Color(0xFFDDF2E8),
                            RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        "ONLINE",
                        color = Color(0xFF328269),
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(Modifier.height(22.dp))

            // Orb principal
            CoachOrb(Modifier.size(104.dp))

            Spacer(Modifier.height(22.dp))

            Text(
                text = "How can I help?",
                color = Color(0xFF202823),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Talk naturally about your workout, nutrition or progress.",
                color = IdleMuted,
                fontSize = 10.sp,
                lineHeight = 15.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "SUGGESTED QUESTIONS",
                color = IdleMuted,
                fontSize = 8.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SuggestionChip(
                    text = "Start my workout",
                    onClick = onOpenConversation
                )
                Spacer(Modifier.width(6.dp))
                SuggestionChip(
                    text = "Log my breakfast",
                    onClick = onOpenConversation
                )
            }

            Spacer(Modifier.height(7.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SuggestionChip(
                    text = "What should I eat tonight?",
                    onClick = onOpenConversation
                )
                Spacer(Modifier.width(6.dp))
                SuggestionChip(
                    text = "How am I doing?",
                    onClick = onOpenConversation
                )
            }

            Spacer(Modifier.height(18.dp))

            // Last conversation
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenConversation() },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    IdleBorder
                )
            ) {
                Column(Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "LAST CONVERSATION",
                            color = IdleMuted,
                            fontSize = 8.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "2 hours ago",
                            color = IdleMuted,
                            fontSize = 8.sp
                        )
                    }

                    Spacer(Modifier.height(7.dp))

                    Text(
                        text = "\"Create a 30-minute workout for me.\"",
                        color = Color(0xFF303A33),
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            // Mic button: navigates to Listening
            Button(
                onClick = onStartVoice,
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = IdleGreen
                )
            ) {
                CoachMicrophoneIcon(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
            }

            Spacer(Modifier.height(6.dp))

            Text(
                text = "TAP TO TALK",
                color = IdleGreen,
                fontSize = 9.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(5.dp))

            Text(
                text = "Type instead",
                modifier = Modifier.clickable {
                    onOpenConversation()
                },
                color = IdleMuted,
                fontSize = 9.sp
            )

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun SuggestionChip(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(30.dp))
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = Color(0xFF303A33),
            fontSize = 9.sp
        )
    }
}

@Composable
private fun CoachOrb(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color(0xFFDCE9D6), CircleShape)
            .padding(5.dp)
            .background(Color(0xFF9ED9B9), CircleShape)
            .padding(5.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF65D6B0),
                        Color(0xFF21856C),
                        Color(0xFF103F35)
                    )
                ),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        CoachWaveform(
            modifier = Modifier
                .width(24.dp)
                .height(20.dp)
        )
    }
}

@Composable
private fun CoachWaveform(
    modifier: Modifier = Modifier
) {
    val heights = listOf(6, 10, 14, 8, 17, 11, 14, 7, 10)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        heights.forEach { barHeight ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(barHeight.dp)
                    .background(Color.White, RoundedCornerShape(50))
            )
        }
    }
}

@Composable
private fun CoachMicrophoneIcon(
    modifier: Modifier = Modifier,
    color: Color = Color.White
) {
    Canvas(modifier = modifier) {
        val centerX = size.width / 2f

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

        drawLine(
            color = color,
            start = Offset(centerX, size.height * 0.76f),
            end = Offset(centerX, size.height * 0.91f),
            strokeWidth = size.width * 0.075f
        )

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
