package ar.edu.ort.vora.ui.workout

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ar.edu.ort.vora.domain.model.Workout

private val PrepDarkGreen = Color(0xFF123F35)
private val PrepMint = Color(0xFF60D5B1)
private val PrepLime = Color(0xFFD0EE69)
private val PrepBackground = Color(0xFFF5F7F4)
private val PrepText = Color(0xFF18352E)

@Composable
fun WorkoutPrepScreen(
    workout: Workout?,
    onStartWorkout: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrepBackground)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = "‹  Volver al entrenamiento",
            color = PrepDarkGreen,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.Start)
                .clickable(onClick = onBack)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 14.dp, vertical = 10.dp)
        )

        if (workout == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CircularProgressIndicator(color = PrepMint)
                    Text(
                        text = "Cargando entrenamiento...",
                        color = PrepText
                    )
                }
            }
        } else {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "WORKOUT PREPARATION",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = PrepDarkGreen
            )

            Text(
                text = "Get Ready",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = PrepText
            )

            Text(
                text = "Prepará tu espacio. Tu próximo entrenamiento está por comenzar.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF64746D)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = PrepDarkGreen
                )
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(PrepLime, CircleShape)
                            .padding(horizontal = 12.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = "YOUR NEXT WORKOUT",
                            color = PrepDarkGreen,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = workout.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "Nivel ${workout.level}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = PrepMint
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        PrepStat(
                            value = "${workout.durationMinutes}",
                            label = "MINUTOS",
                            modifier = Modifier.weight(1f)
                        )

                        PrepStat(
                            value = "${workout.exercises.size}",
                            label = "EJERCICIOS",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Antes de empezar",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrepDarkGreen
                    )

                    Text(
                        text = "✓  Prepará el espacio y el equipo",
                        color = PrepText
                    )

                    Text(
                        text = "✓  Tené agua a mano",
                        color = PrepText
                    )

                    Text(
                        text = "✓  Priorizá la técnica en cada repetición",
                        color = PrepText
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onStartWorkout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrepLime,
                    contentColor = PrepDarkGreen
                )
            ) {
                Text(
                    text = "Start Workout  →",
                    fontWeight = FontWeight.Bold
                )
            }

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "Cancelar",
                    color = PrepDarkGreen
                )
            }
        }
    }
}

@Composable
private fun PrepStat(
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
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = PrepMint
        )
    }
}

