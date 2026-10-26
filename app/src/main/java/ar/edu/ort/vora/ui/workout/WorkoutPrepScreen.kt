
package ar.edu.ort.vora.ui.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.domain.model.Workout

@Composable
fun WorkoutPrepScreen(
    workout: Workout?,
    onStartWorkout: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (workout == null) {
            CircularProgressIndicator()
            Text("Cargando entrenamiento...")
        } else {
            Text(
                text = "Get Ready",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = workout.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text("${workout.durationMinutes} minutos")
            Text("${workout.exercises.size} ejercicios")
            Text("Prepará tu espacio y comenzá cuando estés listo.")

            Button(onClick = onStartWorkout) {
                Text("Start Workout")
            }
        }

        Button(onClick = onBack) {
            Text("Cancelar")
        }
    }
}
