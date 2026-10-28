
package ar.edu.ort.vora.coach

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val SpeakingBackground = Color(0xFF103F35)
private val SpeakingCard = Color(0xFF204D43)
private val SpeakingMint = Color(0xFF65D6B0)
private val SpeakingLime = Color(0xFFC6EF69)
private val SpeakingMuted = Color(0xFFA6C2B8)

@Composable
fun CoachSpeakingScreen(
    response: String = "That sounds like a balanced lunch. I've estimated around 520 calories with approximately 38 grams of protein.",
    onBack: () -> Unit = {},
    onInterrupt: () -> Unit = {},
    onOpenConversation: () -> Unit = {}
) {
    Scaffold(
        containerColor = SpeakingBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SpeakingBackground)
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onBack) {
                    Text("‹", color = Color.White, fontSize = 24.sp)
                }

                Text(
                    text = "VORA Speaking",
                    modifier = Modifier.weight(1f),
                    color = Color.White,
                    fontSize = 12.sp
                )

                Text(
                    text = "SPEAKING",
                    modifier = Modifier
                        .background(SpeakingCard, CircleShape)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    color = SpeakingMint,
                    fontSize = 9.sp
                )
            }

            Spacer(Modifier.height(42.dp))

            // Orb de voz
            Box(
                modifier = Modifier
                    .size(142.dp)
                    .border(3.dp, Color(0xFF286D5B), CircleShape)
                    .padding(10.dp)
                    .background(SpeakingLime, CircleShape)
                    .padding(6.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF65D6B0),
                                Color(0xFF21856C),
                                SpeakingBackground
                            )
                        ),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                SpeakingWaveform()
            }

            Spacer(Modifier.height(34.dp))

            Text(
                text = "\"$response\"",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SpeakingCard, RoundedCornerShape(18.dp))
                    .border(1.dp, Color(0xFF35675A), RoundedCornerShape(18.dp))
                    .padding(16.dp),
                color = Color.White,
                fontSize = 14.sp,
                lineHeight = 21.sp
            )

            Spacer(Modifier.height(24.dp))

            // Barra de progreso decorativa
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(Color(0xFF28574B), RoundedCornerShape(50))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.68f)
                        .fillMaxHeight()
                        .background(SpeakingMint, RoundedCornerShape(50))
                )
            }

            Spacer(Modifier.height(10.dp))

            Row(Modifier.fillMaxWidth()) {
                Text("0:08", color = SpeakingMuted, fontSize = 9.sp)
                Spacer(Modifier.weight(1f))
                Text("-0:14", color = SpeakingMuted, fontSize = 9.sp)
            }

            Spacer(Modifier.height(30.dp))

            // Controles redondos
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundControl(
                    symbol = "↻",
                    onClick = onOpenConversation,
                    background = SpeakingCard
                )

                RoundControl(
                    symbol = "Ⅱ",
                    onClick = onInterrupt,
                    background = SpeakingMint,
                    foreground = SpeakingBackground,
                    size = 68
                )

                RoundControl(
                    symbol = "◎",
                    onClick = onOpenConversation,
                    background = SpeakingCard
                )
            }

            Spacer(Modifier.height(30.dp))

            // Interrumpir la voz lleva a Error
            Button(
                onClick = onInterrupt,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SpeakingLime,
                    contentColor = SpeakingBackground
                )
            ) {
                Text(
                    text = "Tap to Interrupt",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun RoundControl(
    symbol: String,
    onClick: () -> Unit,
    background: Color,
    foreground: Color = Color.White,
    size: Int = 52
) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .background(background, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = symbol,
            color = foreground,
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SpeakingWaveform() {
    val heights = listOf(15, 23, 34, 20, 42, 27, 37, 21, 32, 17)

    Row(
        modifier = Modifier
            .width(52.dp)
            .height(44.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
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
