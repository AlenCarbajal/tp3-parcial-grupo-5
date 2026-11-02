
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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.ort.vora.R

private val NutritionGreen = Color(0xFF123F35)
private val NutritionBackground = Color(0xFFF7F8F5)
private val NutritionMuted = Color(0xFF7A8580)
private val NutritionBorder = Color(0xFFE4EAE6)
private val NutritionLight = Color(0xFFF2F5F2)
private val NutritionMint = Color(0xFFBDEBD7)

@Composable
fun NutritionProgressScreen(
    onBack: () -> Unit = {}
) {
    var period by remember { mutableStateOf("Week") }
    var selectedNutrient by remember { mutableStateOf("Protein") }

    val calories = when (period) {
        "Month" -> stringResource(R.string.common_number, 1920)
        "3 Months" -> stringResource(R.string.common_number, 1870)
        else -> stringResource(R.string.common_number, 1980)
    }

    val protein = when (period) {
        "Month" -> stringResource(R.string.common_value_g, 128)
        "3 Months" -> stringResource(R.string.common_value_g, 124)
        else -> stringResource(R.string.common_value_g, 132)
    }

    val (hitDays, totalDays) = when (period) {
        "Month" -> 22 to 30
        "3 Months" -> 65 to 90
        else -> 5 to 7
    }

    val target = when (selectedNutrient) {
        "Calories" -> stringResource(R.string.common_value_kcal, 2100)
        "Protein" -> stringResource(R.string.common_value_g, 140)
        "Carbs" -> stringResource(R.string.common_value_g, 210)
        else -> stringResource(R.string.common_value_g, 70)
    }

    val dailyValues = when (selectedNutrient) {
        "Calories" -> listOf(90, 75, 45, 85, 95, 30, 80)
        "Protein" -> listOf(100, 100, 0, 100, 100, 0, 100)
        "Carbs" -> listOf(80, 65, 55, 90, 75, 45, 85)
        else -> listOf(70, 80, 60, 75, 85, 50, 65)
    }

    Scaffold(
        containerColor = NutritionBackground
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(NutritionBackground)
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
                            color = NutritionGreen
                        )
                    }

                    Text(
                        text = stringResource(R.string.progress_nutrition_title),
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF18231F)
                    )

                    Spacer(Modifier.weight(1f))

                    Text(
                        text = "•••",
                        color = NutritionGreen,
                        fontSize = 18.sp
                    )
                }
            }

            // Selector de período
            item {
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ProgressPeriods
                        .forEachIndexed { index, (option, label) ->
                            SegmentedButton(
                                selected = period == option,
                                onClick = { period = option },
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = 3
                                ),
                                colors = SegmentedButtonDefaults.colors(
                                    activeContainerColor = NutritionGreen,
                                    activeContentColor = Color.White,
                                    inactiveContainerColor = Color.White,
                                    inactiveContentColor = NutritionMuted,
                                    activeBorderColor = NutritionGreen,
                                    inactiveBorderColor = NutritionBorder
                                )
                            ) {
                                Text(
                                    text = stringResource(label),
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
                        NutritionBorder
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        NutritionSummaryMetric(
                            title = stringResource(R.string.progress_avg_calories),
                            value = "$calories kcal",
                            modifier = Modifier.weight(1f)
                        )

                        SummaryDivider()

                        NutritionSummaryMetric(
                            title = stringResource(R.string.progress_avg_protein),
                            value = protein,
                            modifier = Modifier.weight(1f)
                        )

                        SummaryDivider()

                        NutritionSummaryMetric(
                            title = stringResource(R.string.progress_target_hit),
                            value = pluralStringResource(
                                R.plurals.progress_days_ratio,
                                totalDays,
                                hitDays,
                                totalDays
                            ),
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
                        NutritionBorder
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
                                    NutritionLight,
                                    RoundedCornerShape(10.dp)
                                )
                                .padding(3.dp),
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            listOf(
                                "Calories" to R.string.progress_nutrient_calories,
                                "Protein" to R.string.common_protein,
                                "Carbs" to R.string.common_carbs,
                                "Fat" to R.string.common_fat
                            )
                                .forEach { (nutrient, label) ->
                                    val selected = selectedNutrient == nutrient

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .background(
                                                color = if (selected) {
                                                    NutritionGreen
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
                                            text = stringResource(label),
                                            color = if (selected) {
                                                Color.White
                                            } else {
                                                NutritionMuted
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
                            text = stringResource(
                                when (selectedNutrient) {
                                    "Calories" -> R.string.progress_target_calories
                                    "Protein" -> R.string.progress_target_protein
                                    "Carbs" -> R.string.progress_target_carbs
                                    else -> R.string.progress_target_fat
                                },
                                target
                            ),
                            fontSize = 9.sp,
                            color = NutritionMuted
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val days = WeekDayInitials.map { stringResource(it) }

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
                                                    NutritionMint
                                                } else {
                                                    NutritionBorder
                                                },
                                                shape = CircleShape
                                            )
                                            .background(
                                                color = if (hit) {
                                                    Color(0xFFE7F8F0)
                                                } else {
                                                    NutritionLight
                                                },
                                                shape = CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = day,
                                            fontSize = 9.sp,
                                            color = if (hit) {
                                                NutritionGreen
                                            } else {
                                                NutritionMuted
                                            }
                                        )
                                    }

                                    Text(
                                        text = stringResource(
                                            if (hit) R.string.progress_hit else R.string.progress_miss
                                        ),
                                        fontSize = 7.sp,
                                        color = if (hit) {
                                            NutritionGreen
                                        } else {
                                            NutritionMuted
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
                        containerColor = NutritionGreen
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
                                        Color(0xFF286B58),
                                        CircleShape
                                    )
                                    .border(
                                        1.dp,
                                        NutritionMint,
                                        CircleShape
                                    )
                            )

                            Spacer(Modifier.width(9.dp))

                            Text(
                                text = stringResource(R.string.progress_nutrition_analysis),
                                fontSize = 13.sp,
                                color = NutritionMint,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Text(
                            text = stringResource(R.string.progress_nutrition_insight),
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
                    text = stringResource(R.string.progress_food_habits),
                    fontSize = 10.sp,
                    color = NutritionMuted,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FoodHabitCard(
                        title = stringResource(R.string.progress_food_frequent_lunch),
                        value = stringResource(R.string.progress_food_chicken_rice_bowl),
                        modifier = Modifier.weight(1f)
                    )

                    FoodHabitCard(
                        title = stringResource(R.string.progress_food_top_protein),
                        value = stringResource(R.string.progress_food_whey_chicken),
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
                        title = stringResource(R.string.progress_food_avg_breakfast),
                        value = stringResource(R.string.common_value_kcal, 420),
                        modifier = Modifier.weight(1f)
                    )

                    FoodHabitCard(
                        title = stringResource(R.string.progress_food_avg_dinner),
                        value = stringResource(R.string.common_value_kcal, 610),
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
            color = NutritionMuted,
            maxLines = 1
        )

        Text(
            text = value,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF18231F),
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
            .background(NutritionBorder)
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
            NutritionBorder
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
                color = NutritionMuted
            )

            Text(
                text = value,
                fontSize = 11.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF18231F)
            )
        }
    }
}
