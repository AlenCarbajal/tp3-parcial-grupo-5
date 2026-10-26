
package ar.edu.ort.vora.ui.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.domain.model.Workout

@Composable
fun WorkoutCompletedScreen(
    workout: Workout?,
    onHome: () -> Unit,
    onBackToWorkout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Completed!",
            style = MaterialTheme.typography.headlineLarge
        )
        Text("You did well!")

        workout?.let {
            Text(it.name, style = MaterialTheme.typography.titleLarge)
            Text("Duración: ${it.durationMinutes} minutos")
            Text("Ejercicios: ${it.exercises.size}")
        }

        Button(onClick = onHome) {
            Text("Done")
        }

        Button(onClick = onBackToWorkout) {
            Text("Back to Workout")
        }
    }
}
