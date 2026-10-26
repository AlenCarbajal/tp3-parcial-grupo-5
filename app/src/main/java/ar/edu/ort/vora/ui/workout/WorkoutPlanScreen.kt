
package ar.edu.ort.vora.ui.workout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.domain.model.Workout

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
            .padding(16.dp)
    ) {
        Text(
            text = "Your Plan",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(onClick = onBack) {
            Text("Volver")
        }

        if (workouts.isEmpty()) {
            Text("No hay entrenamientos disponibles.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(workouts, key = { it.id }) { workout ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                text = workout.name,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.clickable {
                                    onWorkoutClick(workout.id)
                                }
                            )
                            Text("Nivel: ${workout.level}")
                            Text("Duración: ${workout.durationMinutes} minutos")
                            Text("Ejercicios: ${workout.exerciseCount}")

                            Button(
                                onClick = {
                                    onStartWorkout(workout.id)
                                }
                            ) {
                                Text("Start Workout")
                            }
                        }
                    }
                }
            }
        }
    }
}
