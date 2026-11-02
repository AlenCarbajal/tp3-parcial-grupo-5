
package ar.edu.ort.vora.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.ort.vora.ui.theme.VoraBackground
import ar.edu.ort.vora.ui.theme.VoraBorder
import ar.edu.ort.vora.ui.theme.VoraGreen
import ar.edu.ort.vora.ui.theme.VoraGreenAccent
import ar.edu.ort.vora.ui.theme.VoraMintLight
import ar.edu.ort.vora.ui.theme.VoraMintSoft
import ar.edu.ort.vora.ui.theme.VoraSurfaceMuted
import ar.edu.ort.vora.ui.theme.VoraTextPrimary
import ar.edu.ort.vora.ui.theme.VoraTextSecondary

@Composable
fun NutritionProgressScreen(
    onBack: () -> Unit = {}
) {
    var period by remember { mutableStateOf("Week") }
    var selectedNutrient by remember { mutableStateOf("Protein") }

    val calories = when (period) {
        "Month" -> "1,920"
        "3 Months" -> "1,870"
        else -> "1,980"
    }

    val protein = when (period) {
        "Month" -> "128g"
        "3 Months" -> "124g"
        else -> "132g"
    }

    val daysHit = when (period) {
        "Month" -> "22 / 30 days"
        "3 Months" -> "65 / 90 days"
        else -> "5 / 7 days"
    }

    val target = when (selectedNutrient) {
        "Calories" -> "2,100 kcal"
        "Protein" -> "140g"
        "Carbs" -> "210g"
        else -> "70g"
    }

    val dailyValues = when (selectedNutrient) {
        "Calories" -> listOf(90, 75, 45, 85, 95, 30, 80)
        "Protein" -> listOf(100, 100, 0, 100, 100, 0, 100)
        "Carbs" -> listOf(80, 65, 55, 90, 75, 45, 85)
        else -> listOf(70, 80, 60, 75, 85, 50, 65)
    }

    Scaffold(
        containerColor = VoraBackground
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(VoraBackground)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(
                top = 8.dp,
                bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Encabezado
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onBack,
                        contentPadding = PaddingValues(
                            horizontal = 4.dp,
                            vertical = 8.dp
                        )
                    ) {
                        Text(
                            text = "‹",
                            fontSize = 25.sp,
                            color = VoraGreen
                        )
                    }

                    Text(
                        text = "Nutrition progress",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = VoraTextPrimary
                    )

                    Spacer(Modifier.weight(1f))

                    Text(
                        text = "•••",
                        color = VoraGreen,
                        fontSize = 18.sp
                    )
                }
            }

            // Selector de período
            item {
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("Week", "Month", "3 Months")
                        .forEachIndexed { index, option ->
                            SegmentedButton(
                                selected = period == option,
                                onClick = { period = option },
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = 3
                                ),
                                colors = SegmentedButtonDefaults.colors(
                                    activeContainerColor = VoraGreen,
                                    activeContentColor = Color.White,
                                    inactiveContainerColor = Color.White,
                                    inactiveContentColor = VoraTextSecondary,
                                    activeBorderColor = VoraGreen,
                                    inactiveBorderColor = VoraBorder
                                )
                            ) {
                                Text(
                                    text = option,
                                    fontSize = 10.sp,
                                    maxLines = 1
                                )
                            }
                        }
                }
            }

            // Indicadores principales
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        VoraBorder
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        NutritionSummaryMetric(
                            title = "AVG CALORIES",
                            value = "$calories kcal",
                            modifier = Modifier.weight(1f)
                        )

                        SummaryDivider()

                        NutritionSummaryMetric(
                            title = "AVG PROTEIN",
                            value = protein,
                            modifier = Modifier.weight(1f)
                        )

                        SummaryDivider()

                        NutritionSummaryMetric(
                            title = "TARGET HIT",
                            value = daysHit,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Selector de nutriente y objetivo diario
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        VoraBorder
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    VoraSurfaceMuted,
                                    RoundedCornerShape(10.dp)
                                )
                                .padding(3.dp),
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            listOf("Calories", "Protein", "Carbs", "Fat")
                                .forEach { nutrient ->
                                    val selected = selectedNutrient == nutrient

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .background(
                                                color = if (selected) {
                                                    VoraGreen
                                                } else {
                                                    Color.Transparent
                                                },
                                                shape = RoundedCornerShape(7.dp)
                                            )
                                            .clickable {
                                                selectedNutrient = nutrient
                                            }
                                            .padding(
                                                vertical = 8.dp,
                                                horizontal = 2.dp
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = nutrient,
                                            color = if (selected) {
                                                Color.White
                                            } else {
                                                VoraTextSecondary
                                            },
                                            fontSize = 9.sp,
                                            fontWeight = if (selected) {
                                                FontWeight.SemiBold
                                            } else {
                                                FontWeight.Normal
                                            },
                                            maxLines = 1
                                        )
                                    }
                                }
                        }

                        Text(
                            text = when (selectedNutrient) {
                                "Calories" -> "DAILY CALORIE TARGET ($target)"
                                "Protein" -> "DAILY AVG PROTEIN TARGET ($target)"
                                "Carbs" -> "DAILY CARBS TARGET ($target)"
                                else -> "DAILY FAT TARGET ($target)"
                            },
                            fontSize = 9.sp,
                            color = VoraTextSecondary
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val days = listOf("M", "T", "W", "T", "F", "S", "S")

                            days.forEachIndexed { index, day ->
                                val hit = dailyValues[index] >= 80

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(23.dp)
                                            .border(
                                                width = 1.dp,
                                                color = if (hit) {
                                                    VoraMintSoft
                                                } else {
                                                    VoraBorder
                                                },
                                                shape = CircleShape
                                            )
                                            .background(
                                                color = if (hit) {
                                                    VoraMintLight
                                                } else {
                                                    VoraSurfaceMuted
                                                },
                                                shape = CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = day,
                                            fontSize = 9.sp,
                                            color = if (hit) {
                                                VoraGreen
                                            } else {
                                                VoraTextSecondary
                                            }
                                        )
                                    }

                                    Text(
                                        text = if (hit) "HIT" else "MISS",
                                        fontSize = 7.sp,
                                        color = if (hit) {
                                            VoraGreen
                                        } else {
                                            VoraTextSecondary
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Análisis de VORA
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = VoraGreen
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(25.dp)
                                    .background(
                                        VoraGreenAccent,
                                        CircleShape
                                    )
                                    .border(
                                        1.dp,
                                        VoraMintSoft,
                                        CircleShape
                                    )
                            )

                            Spacer(Modifier.width(9.dp))

                            Text(
                                text = "Nutrition Analysis",
                                fontSize = 13.sp,
                                color = VoraMintSoft,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Text(
                            text = "\"Your protein intake has improved this week, " +
                                    "especially on workout days. Keeping this consistent " +
                                    "will better support your strength goal.\"",
                            fontSize = 11.sp,
                            lineHeight = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }

            // Hábitos alimentarios
            item {
                Text(
                    text = "FOOD HABITS & PATTERNS",
                    fontSize = 10.sp,
                    color = VoraTextSecondary,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FoodHabitCard(
                        title = "Frequent lunch",
                        value = "Chicken & Rice Bowl",
                        modifier = Modifier.weight(1f)
                    )

                    FoodHabitCard(
                        title = "Top protein source",
                        value = "Whey & Chicken Breast",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FoodHabitCard(
                        title = "Avg breakfast",
                        value = "420 kcal",
                        modifier = Modifier.weight(1f)
                    )

                    FoodHabitCard(
                        title = "Avg dinner",
                        value = "610 kcal",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun NutritionSummaryMetric(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            fontSize = 7.sp,
            color = VoraTextSecondary,
            maxLines = 1
        )

        Text(
            text = value,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = VoraTextPrimary,
            maxLines = 1
        )
    }
}

@Composable
private fun SummaryDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(30.dp)
            .background(VoraBorder)
    )
}

@Composable
private fun FoodHabitCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            VoraBorder
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(11.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = title,
                fontSize = 9.sp,
                color = VoraTextSecondary
            )

            Text(
                text = value,
                fontSize = 11.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight.Medium,
                color = VoraTextPrimary
            )
        }
    }
}
