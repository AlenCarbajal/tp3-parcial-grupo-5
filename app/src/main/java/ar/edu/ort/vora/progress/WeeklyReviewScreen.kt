

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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.ort.vora.R
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
                            text = stringResource(R.string.progress_weekly_title),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )

                        Text(
                            text = stringResource(R.string.progress_weekly_subtitle),
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
                            text = stringResource(R.string.progress_weekly_headline),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = VoraLime
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            WeeklySummaryMetric(
                                title = stringResource(R.string.progress_workouts_caps),
                                value = pluralStringResource(R.plurals.progress_sessions_count, 4, 4),
                                modifier = Modifier.weight(1f)
                            )

                            WeeklySummaryMetric(
                                title = stringResource(R.string.progress_avg_steps),
                                value = pluralStringResource(R.plurals.progress_steps_count, 7842, stringResource(R.string.common_number, 7842)),
                                modifier = Modifier.weight(1f)
                            )

                            WeeklySummaryMetric(
                                title = stringResource(R.string.progress_protein_caps),
                                value = stringResource(R.string.progress_protein_daily, stringResource(R.string.common_value_g, 132)),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Lo que salió bien
            item {
                Text(
                    text = stringResource(R.string.progress_went_well),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = VoraLime
                )
            }

            item {
                WeeklyAchievement(
                    title = stringResource(R.string.progress_well_consistent_title),
                    detail = stringResource(R.string.progress_well_consistent_detail)
                )
            }

            item {
                WeeklyAchievement(
                    title = stringResource(R.string.progress_well_protein_title),
                    detail = stringResource(R.string.progress_well_protein_detail)
                )
            }

            item {
                WeeklyAchievement(
                    title = stringResource(R.string.progress_well_volume_title),
                    detail = stringResource(R.string.progress_well_volume_detail)
                )
            }

            // Lo que se puede mejorar
            item {
                Spacer(Modifier.height(2.dp))

                Text(
                    text = stringResource(R.string.progress_to_improve),
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
                                text = stringResource(R.string.progress_improve_dinner_title),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }

                        Text(
                            text = stringResource(R.string.progress_improve_dinner_detail),
                            fontSize = 10.sp,
                            lineHeight = 15.sp,
                            color = VoraOnGreenMuted
                        )

                        Text(
                            text = stringResource(R.string.progress_dinner_ideas),
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
                            text = stringResource(R.string.progress_next_week_recommendations),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = VoraOnGreenMuted
                        )

                        WeeklyRecommendation(
                            text = stringResource(R.string.progress_reco_schedule)
                        )

                        WeeklyRecommendation(
                            text = stringResource(R.string.progress_reco_protein)
                        )

                        WeeklyRecommendation(
                            text = stringResource(R.string.progress_reco_steps)
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
                                text = stringResource(R.string.progress_next_week_plan),
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
                        text = "🎙  " + stringResource(R.string.progress_ask_vora),
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
                        text = stringResource(R.string.common_done),
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
