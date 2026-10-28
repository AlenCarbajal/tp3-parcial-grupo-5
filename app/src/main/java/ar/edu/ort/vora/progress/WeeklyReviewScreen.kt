

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

private val WeeklyGreen = Color(0xFF103F35)
private val WeeklyBackground = Color(0xFF103F35)
private val WeeklyCardGreen = Color(0xFF204D43)
private val WeeklyMuted = Color(0xFFA6C2B8)
private val WeeklyMint = Color(0xFFBDEB70)
private val WeeklyBorder = Color(0xFF38675B)

@Composable
fun WeeklyReviewScreen(
    onBack: () -> Unit = {},
    onAskCoach: () -> Unit = {}
) {
    Scaffold(
        containerColor = WeeklyBackground
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(WeeklyBackground)
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
                            color = WeeklyMuted
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
                            color = WeeklyMuted
                        )
                    }

                    // Indicador visual de VORA
                    Box(
                        modifier = Modifier
                            .size(25.dp)
                            .background(
                                color = Color(0xFF286B58),
                                shape = CircleShape
                            )
                            .border(
                                width = 1.dp,
                                color = WeeklyMint,
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
                        containerColor = WeeklyCardGreen
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        WeeklyBorder
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
                            color = WeeklyMint
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
                    color = WeeklyMint
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
                    color = WeeklyMint
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = WeeklyCardGreen
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        WeeklyBorder
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
                                color = WeeklyMint
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
                            color = WeeklyMuted
                        )

                        Text(
                            text = "Get dinner ideas  →",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = WeeklyMint
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
                            color = WeeklyMuted
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
                                .background(Color(0xFFE7ECE8))
                        )

                        TextButton(
                            onClick = onBack,
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "View next week's plan  →",
                                fontSize = 10.sp,
                                color = WeeklyGreen
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
                        containerColor = WeeklyMint,
                        contentColor = WeeklyGreen
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
                        containerColor = WeeklyCardGreen,
                        contentColor = Color.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        WeeklyBorder
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
            color = WeeklyMuted,
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
                    color = Color(0xFF286B58),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "✓",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = WeeklyMint
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
                color = WeeklyMuted
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
            color = WeeklyGreen
        )

        Spacer(Modifier.width(7.dp))

        Text(
            text = text,
            fontSize = 10.sp,
            lineHeight = 14.sp,
            color = Color(0xFF26332D)
        )
    }
}
