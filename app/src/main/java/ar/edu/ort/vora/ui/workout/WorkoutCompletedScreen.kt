
package ar.edu.ort.vora.ui.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.R
import ar.edu.ort.vora.domain.model.Workout

private val CompletedDarkGreen = Color(0xFF123F35)
private val CompletedMint = Color(0xFF60D5B1)
private val CompletedLime = Color(0xFFD0EE69)
private val CompletedBackground = Color(0xFFF5F7F4)
private val CompletedText = Color(0xFF18352E)

@Composable
fun WorkoutCompletedScreen(
    workout: Workout?,
    onHome: () -> Unit,
    onBackToWorkout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CompletedBackground)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = CompletedMint.copy(alpha = 0.25f),
                    shape = CircleShape
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "✓",
                color = CompletedDarkGreen,
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.workout_completed_label),
            color = CompletedDarkGreen,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.workout_completed_title),
            color = CompletedText,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.workout_completed_message),
            color = CompletedText,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (workout != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CompletedDarkGreen
                )
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = workout.name,
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.workout_duration_caps),
                            color = CompletedMint,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = pluralStringResource(R.plurals.workout_minutes_count, workout.durationMinutes, workout.durationMinutes),
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.workout_exercises_header),
                            color = CompletedMint,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = pluralStringResource(R.plurals.common_exercise_count, workout.exercises.size, workout.exercises.size),
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onBackToWorkout,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CompletedDarkGreen,
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.workout_back_to_workout),
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }
}
