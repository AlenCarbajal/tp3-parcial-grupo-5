

package ar.edu.ort.vora.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.ort.vora.ui.theme.VoraBorder
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraGreenAccent
import ar.edu.ort.vora.ui.theme.VoraGreenBorder
import ar.edu.ort.vora.ui.theme.VoraGreenRaised
import ar.edu.ort.vora.ui.theme.VoraLime
import ar.edu.ort.vora.ui.theme.VoraOnGreenMuted
import ar.edu.ort.vora.ui.theme.VoraTextPrimary

@Composable
fun WeeklyReviewScreen(
    onBack: () -> Unit = {},
    onAskCoach: () -> Unit = {}
) {
    Scaffold(
        containerColor = VoraGreen
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(VoraGreen)
                .padding(padding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(
                top = 8.dp,
                bottom = 20.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Encabezado
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 42.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onBack,
                        contentPadding = PaddingValues(
                            horizontal = 4.dp,
                            vertical = 4.dp
                        )
                    ) {
                        Text(
                            text = "‹",
                            fontSize = 25.sp,
                            color = VoraOnGreenMuted
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "Your weekly review",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )

                        Text(
                            text = "Here's what changed this week.",
                            fontSize = 10.sp,
                            color = VoraOnGreenMuted
                        )
                    }

                    // Indicador visual de VORA
                    Box(
                        modifier = Modifier
                            .size(25.dp)
                            .background(
                                color = VoraGreenAccent,
                                shape = CircleShape
                            )
                            .border(
                                width = 1.dp,
                                color = VoraLime,
                                shape = CircleShape
                            )
                    )
                }
            }

            // Resumen semanal
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(17.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = VoraGreenRaised
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        VoraGreenBorder
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(13.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "\"You had a strong week.\"",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = VoraLime
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            WeeklySummaryMetric(
                                title = "WORKOUTS",
                                value = "4 sessions",
                                modifier = Modifier.weight(1f)
                            )

                            WeeklySummaryMetric(
                                title = "AVG STEPS",
                                value = "7,842 steps",
                                modifier = Modifier.weight(1f)
                            )

                            WeeklySummaryMetric(
                                title = "PROTEIN",
                                value = "132g daily",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Lo que salió bien
            item {
                Text(
                    text = "WHAT WENT WELL",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = VoraLime
                )
            }

            item {
                WeeklyAchievement(
                    title = "You stayed consistent",
                    detail = "Four completed workouts helped you maintain your weekly training rhythm."
                )
            }

            item {
                WeeklyAchievement(
                    title = "Your protein improved",
                    detail = "You reached your protein target on 5 of 7 days."
                )
            }

            item {
                WeeklyAchievement(
                    title = "Your training volume increased",
                    detail = "You completed 8% more volume than last week."
                )
            }

            // Lo que se puede mejorar
            item {
                Spacer(Modifier.height(2.dp))

                Text(
                    text = "WHAT TO IMPROVE",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = VoraLime
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = VoraGreenRaised
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        VoraGreenBorder
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "ⓘ",
                                fontSize = 15.sp,
                                color = VoraLime
                            )

                            Spacer(Modifier.width(7.dp))

                            Text(
                                text = "Dinner consistency",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }

                        Text(
                            text = "Your protein intake tends to drop at dinner. " +
                                    "Try planning a protein-rich dinner before the evening.",
                            fontSize = 10.sp,
                            lineHeight = 15.sp,
                            color = VoraOnGreenMuted
                        )

                        Text(
                            text = "Get dinner ideas  →",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = VoraLime
                        )
                    }
                }
            }

            // Recomendaciones para la semana siguiente
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(13.dp),
                        verticalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        Text(
                            text = "NEXT WEEK'S RECOMMENDATIONS",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = VoraOnGreenMuted
                        )

                        WeeklyRecommendation(
                            text = "Keep your current workout schedule."
                        )

                        WeeklyRecommendation(
                            text = "Try to reach 140g protein on at least 6 days."
                        )

                        WeeklyRecommendation(
                            text = "Aim for 8,000+ steps on non-training days."
                        )

                        Spacer(
                            Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(VoraBorder)
                        )

                        TextButton(
                            onClick = onBack,
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "View next week's plan  →",
                                fontSize = 10.sp,
                                color = VoraGreen
                            )
                        }
                    }
                }
            }

            // Ask VORA
            item {
                Button(
                    onClick = onAskCoach,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(43.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VoraLime,
                        contentColor = VoraGreen
                    )
                ) {
                    Text(
                        text = "🎙  Ask VORA",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Done
            item {
                Button(
                    onClick = onBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VoraGreenRaised,
                        contentColor = Color.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        VoraGreenBorder
                    )
                ) {
                    Text(
                        text = "Done",
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun WeeklySummaryMetric(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            fontSize = 8.sp,
            color = VoraOnGreenMuted,
            maxLines = 1
        )

        Text(
            text = value,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            maxLines = 1
        )
    }
}

@Composable
private fun WeeklyAchievement(
    title: String,
    detail: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .padding(top = 1.dp)
                .size(18.dp)
                .background(
                    color = VoraGreenAccent,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "✓",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = VoraLime
            )
        }

        Spacer(Modifier.width(9.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Text(
                text = detail,
                fontSize = 10.sp,
                lineHeight = 14.sp,
                color = VoraOnGreenMuted
            )
        }
    }
}

@Composable
private fun WeeklyRecommendation(
    text: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "•",
            fontSize = 13.sp,
            color = VoraGreen
        )

        Spacer(Modifier.width(7.dp))

        Text(
            text = text,
            fontSize = 10.sp,
            lineHeight = 14.sp,
            color = VoraTextPrimary
        )
    }
}
