package ar.edu.ort.vora.ui.workout

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.domain.model.Workout

private val ActiveDarkGreen = Color(0xFF123F35)
private val ActiveMint = Color(0xFF60D5B1)
private val ActiveLime = Color(0xFFD0EE69)
private val ActiveBackground = Color(0xFFF5F7F4)
private val ActiveText = Color(0xFF18352E)

@Composable
fun WorkoutActiveScreen(
    workout: Workout?,
    onGuideClick: (String) -> Unit,
    onFinish: () -> Unit
) {
    var currentIndex by rememberSaveable(workout?.id) {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ActiveBackground)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        if (workout == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Cargando entrenamiento...",
                    color = ActiveText
                )
            }
        } else if (workout.exercises.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "No hay ejercicios disponibles",
                        style = MaterialTheme.typography.titleLarge,
                        color = ActiveText
                    )

                    Button(
                        onClick = onFinish,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ActiveDarkGreen
                        )
                    ) {
                        Text("Finish Workout")
                    }
                }
            }
        } else {
            val exercise = workout.exercises[
                currentIndex.coerceIn(workout.exercises.indices)
            ]

            Text(
                text = "YOUR WORKOUT",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = ActiveDarkGreen
            )

            Text(
                text = "Active Workout",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = ActiveText
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = workout.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = ActiveDarkGreen
                    )

                    Text(
                        text = "${currentIndex + 1}/${workout.exercises.size}",
                        color = ActiveDarkGreen,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(
                            color = Color(0xFFDDE7E1),
                            shape = CircleShape
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(
                                (currentIndex + 1).toFloat() /
                                        workout.exercises.size
                            )
                            .height(8.dp)
                            .background(
                                color = ActiveMint,
                                shape = CircleShape
                            )
                    )
                }

                Text(
                    text = "Ejercicio ${currentIndex + 1} de " +
                            workout.exercises.size,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(
                    containerColor = ActiveDarkGreen
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFF205347),
                                shape = CircleShape
                            )
                            .padding(horizontal = 18.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = "EXERCISE ${currentIndex + 1}",
                            color = ActiveMint,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = exercise.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = exercise.muscleGroup,
                        style = MaterialTheme.typography.bodyMedium,
                        color = ActiveMint,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(26.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ActiveStat(
                            value = "${exercise.sets}",
                            label = "SERIES",
                            modifier = Modifier.weight(1f)
                        )

                        ActiveStat(
                            value = "${exercise.reps}",
                            label = "REPS",
                            modifier = Modifier.weight(1f)
                        )

                        ActiveStat(
                            value = "${exercise.restSeconds}s",
                            label = "DESCANSO",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(22.dp))

                    OutlinedButton(
                        onClick = { onGuideClick(exercise.id) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = "Exercise Guide",
                            color = Color.White
                        )
                    }
                }
            }

            Button(
                onClick = {
                    if (currentIndex < workout.exercises.lastIndex) {
                        currentIndex++
                    } else {
                        onFinish()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ActiveLime,
                    contentColor = ActiveDarkGreen
                )
            ) {
                Text(
                    text = if (currentIndex < workout.exercises.lastIndex) {
                        "Next Exercise  →"
                    } else {
                        "Finish Workout  ✓"
                    },
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ActiveStat(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = Color(0xFF205347),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(vertical = 14.dp, horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = ActiveMint,
            textAlign = TextAlign.Center
        )
    }
}
