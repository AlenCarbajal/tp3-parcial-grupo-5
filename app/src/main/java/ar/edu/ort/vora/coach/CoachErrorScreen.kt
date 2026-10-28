
package ar.edu.ort.vora.coach

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ErrorGreen = Color(0xFF103F35)
private val ErrorBackground = Color(0xFFF7F8F5)
private val ErrorMuted = Color(0xFF727970)
private val ErrorMint = Color(0xFF65D6B0)

@Composable
fun CoachErrorScreen(
    onBack: () -> Unit = {},
    onTryAgain: () -> Unit = {},
    onTypeInstead: () -> Unit = {},
    onContinueDemo: () -> Unit = {}
) {
    Scaffold(
        containerColor = ErrorBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ErrorBackground)
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onBack) {
                    Text("‹", color = ErrorGreen, fontSize = 23.sp)
                }

                Text(
                    text = "System Alert",
                    color = Color(0xFF202823),
                    fontSize = 12.sp
                )
            }

            Spacer(Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .size(88.dp)
                    .background(Color(0xFFE0E5E1), CircleShape)
                    .padding(7.dp)
                    .background(Color(0xFF9AA39D), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "↻",
                    color = Color.White,
                    fontSize = 44.sp
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Sorry, I didn't catch that",
                color = Color(0xFF202823),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(9.dp))

            Text(
                text = "Try speaking again or type your request instead.\n" +
                        "Make sure your microphone is enabled.",
                color = ErrorMuted,
                fontSize = 10.sp,
                lineHeight = 15.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(27.dp)
                            .background(Color(0xFFFFE1E1), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "!",
                            color = Color(0xFFD85D65),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(Modifier.size(8.dp))

                    Text(
                        text = "Microphone access needed",
                        color = Color(0xFF202823),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "VORA uses your microphone to listen to your " +
                            "voice commands and provide real-time hands-free " +
                            "coaching.",
                    color = ErrorMuted,
                    fontSize = 10.sp,
                    lineHeight = 15.sp
                )

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onTryAgain,
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ErrorGreen
                        )
                    ) {
                        Text("Allow Access", fontSize = 10.sp)
                    }

                    OutlinedButton(
                        onClick = onBack,
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = "Not Now",
                            color = ErrorGreen,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onTryAgain,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ErrorMint,
                    contentColor = ErrorGreen
                )
            ) {
                Text("Try again", fontSize = 12.sp)
            }

            Spacer(Modifier.height(10.dp))

            Button(
                onClick = onContinueDemo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ErrorGreen,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Continue demo",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(Modifier.height(8.dp))

            TextButton(onClick = onTypeInstead) {
                Text(
                    text = "Type instead",
                    color = ErrorMuted,
                    fontSize = 10.sp
                )
            }

            Spacer(Modifier.height(4.dp))
        }
    }
}
