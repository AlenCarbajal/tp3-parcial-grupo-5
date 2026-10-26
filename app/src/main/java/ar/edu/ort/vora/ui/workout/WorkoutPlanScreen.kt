
package ar.edu.ort.vora.ui.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.domain.model.Workout

private val PlanDarkGreen = Color(0xFF123F35)
private val PlanMint = Color(0xFF60D5B1)
private val PlanLime = Color(0xFFD0EE69)
private val PlanBackground = Color(0xFFF5F7F4)
private val PlanText = Color(0xFF18352E)

@Composable
fun WorkoutPlanScreen(
    workouts: List<Workout>,
    onWorkoutClick: (String) -> Unit,
    onStartWorkout: (String) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PlanBackground)
            .padding(24.dp)
    ) {
        Text(
            text = "YOUR PLAN",
            color = PlanDarkGreen,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Tu plan semanal",
            color = PlanText,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 6.dp, bottom = 8.dp)
        )

        Text(
            text = "Elegí un entrenamiento para consultar sus detalles o comenzar.",
            color = PlanText,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        if (workouts.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Todavía no hay entrenamientos",
                        color = PlanDarkGreen,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Cuando haya entrenamientos disponibles, aparecerán acá.",
                        color = PlanText
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(workouts, key = { it.id }) { workout ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = PlanDarkGreen
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = workout.name,
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable {
                                    onWorkoutClick(workout.id)
                                }
                            )

                            Text(
                                text = "Nivel: ${workout.level}",
                                color = PlanMint,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Text(
                                text = "${workout.durationMinutes} minutos  •  ${workout.exerciseCount} ejercicios",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Button(
                                onClick = { onWorkoutClick(workout.id) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = PlanDarkGreen
                                )
                            ) {
                                Text(
                                    text = "Ver entrenamiento",
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Button(
                                onClick = { onStartWorkout(workout.id) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PlanLime,
                                    contentColor = PlanDarkGreen
                                )
                            ) {
                                Text(
                                    text = "Comenzar entrenamiento",
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PlanDarkGreen,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Volver",
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }
    }
}

