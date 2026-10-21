
package ar.edu.ort.vora.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val VerdeOscuro = Color(0xFF123F35)
private val Menta = Color(0xFF65D6B0)
private val Fondo = Color(0xFFF7F8F5)
private val Gris = Color(0xFF6B7773)
private val Borde = Color(0xFFE2E8E4)
private val Rojo = Color(0xFFE35D60)
private val Lima = Color(0xFFC7E95B)

@Composable
fun ProgressScreen(
    onStartWorkout: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = "TUESDAY, 8 SEPTEMBER",
                fontSize = 12.sp,
                color = Gris,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Today's Overview",
                fontSize = 27.sp,
                lineHeight = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF18231F)
            )
        }

        DailyCompletionCard()

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = VerdeOscuro
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "✣  VORA ADVICE",
                    color = Menta,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "\"Completing today’s intermediate workout and a protein-rich dinner will secure your optimal target recovery.\"",
                    color = Color.White,
                    fontSize = 14.sp,
                    lineHeight = 21.sp
                )
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = "Category Breakdowns",
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF18231F)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    Borde
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "WORKOUT",
                            color = Gris,
                            fontSize = 11.sp
                        )
                        Text(
                            text = "NOT STARTED",
                            color = Rojo,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Text(
                        text = "Upper Body Strength",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF18231F)
                    )

                    Text(
                        text = "42 min • 5 exercises • Intermediate",
                        fontSize = 12.sp,
                        color = Gris
                    )

                    Button(
                        onClick = onStartWorkout,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VerdeOscuro
                        )
                    ) {
                        Text(
                            text = "Start Workout",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, Borde)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "CURRENT GOAL",
                        fontSize = 11.sp,
                        color = Gris
                    )
                    Text(
                        text = "Build strength",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF18231F)
                    )
                }

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(42.dp)
                        .background(Borde)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "WEEKLY TARGET",
                        fontSize = 11.sp,
                        color = Gris
                    )
                    Text(
                        text = "4 / 5 Workouts",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF18231F)
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun DailyCompletionCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, Borde)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "DAILY COMPLETION",
                        fontSize = 12.sp,
                        color = Gris
                    )
                    Text(
                        text = "72%",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF18231F)
                    )
                }

                CircularCompletion(
                    progress = 0.72f,
                    modifier = Modifier.size(60.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Borde)
            )

            CompletionRow(
                color = Rojo,
                title = "Workout Progress",
                detail = "Not started",
                percent = "0%"
            )
            CompletionRow(
                color = Menta,
                title = "Nutrition Target",
                detail = "1,420 / 2,100 kcal",
                percent = "78%"
            )
            CompletionRow(
                color = VerdeOscuro,
                title = "Activity Target",
                detail = "7,842 steps",
                percent = "84%"
            )
            CompletionRow(
                color = Lima,
                title = "Hydration Intake",
                detail = "1.6 / 2.5 Litres",
                percent = "65%"
            )
        }
    }
}

@Composable
private fun CompletionRow(
    color: Color,
    title: String,
    detail: String,
    percent: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = title,
            fontSize = 12.sp,
            color = Color(0xFF18231F),
            modifier = Modifier.weight(1f)
        )

        Text(
            text = detail,
            fontSize = 11.sp,
            color = Gris
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = percent,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF18231F)
        )
    }
}

@Composable
private fun CircularCompletion(
    progress: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.drawBehind {
            val stroke = 5.dp.toPx()
            val diameter = size.minDimension - stroke
            val topLeft = androidx.compose.ui.geometry.Offset(
                (size.width - diameter) / 2,
                (size.height - diameter) / 2
            )

            drawArc(
                color = Fondo,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = Size(diameter, diameter),
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = stroke,
                    cap = StrokeCap.Round
                )
            )

            drawArc(
                color = VerdeOscuro,
                startAngle = -90f,
                sweepAngle = 360f * progress.coerceIn(0f, 1f),
                useCenter = false,
                topLeft = topLeft,
                size = Size(diameter, diameter),
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = stroke,
                    cap = StrokeCap.Round
                )
            )
        }
    )
}
