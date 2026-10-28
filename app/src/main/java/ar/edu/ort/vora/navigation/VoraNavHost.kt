
package ar.edu.ort.vora.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import ar.edu.ort.vora.coach.CoachScreen
import ar.edu.ort.vora.coach.CoachListeningScreen
import ar.edu.ort.vora.coach.CoachProcessingScreen
import ar.edu.ort.vora.coach.CoachSpeakingScreen
import ar.edu.ort.vora.coach.CoachConversationScreen
import ar.edu.ort.vora.coach.CoachErrorScreen

import ar.edu.ort.vora.progress.HealthTrendsScreen
import ar.edu.ort.vora.progress.NutritionProgressScreen
import ar.edu.ort.vora.progress.ProgressDashboardScreen
import ar.edu.ort.vora.progress.TodaysOverviewScreen
import ar.edu.ort.vora.progress.WeeklyReviewScreen
import ar.edu.ort.vora.progress.WorkoutProgressScreen

@Composable
fun VoraNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {

        // LOGIN
        composable(Routes.LOGIN) {
            PlaceholderScreen(
                title = "Iniciar sesión",
                links = arrayOf(
                    Routes.HOME to "Entrar",
                    Routes.SIGNUP to "Crear cuenta"
                ),
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }

        // SIGN UP
        composable(Routes.SIGNUP) {
            PlaceholderScreen(
                title = "Crear cuenta",
                links = arrayOf(
                    Routes.LOGIN to "Volver al inicio"
                ),
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }

        // HOME
        composable(Routes.HOME) {
            PlaceholderScreen(
                title = "Inicio",
                links = arrayOf(
                    Routes.WORKOUT to "Entrenamiento",
                    Routes.NUTRITION to "Nutrición",
                    Routes.PROGRESS to "Progreso",
                    Routes.PROFILE to "Perfil",
                    Routes.COACH to "VORA Coach",
                    Routes.NOTIFICATIONS to "Notificaciones"
                ),
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }

        // NOTIFICATIONS
        composable(Routes.NOTIFICATIONS) {
            PlaceholderScreen(
                title = "Notificaciones",
                links = arrayOf(
                    Routes.HOME to "Volver al inicio"
                ),
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }

        // VORA COACH - IDLE
        composable(Routes.COACH) {
            CoachScreen(
                onBack = {
                    navController.popBackStack()
                },
                onStartVoice = {
                    navController.navigate(Routes.COACH_LISTENING)
                },
                onOpenConversation = {
                    navController.navigate(Routes.COACH_CONVERSATION)
                }
            )
        }

        // VORA COACH - LISTENING
        composable(Routes.COACH_LISTENING) {
            CoachListeningScreen(
                onCancel = {
                    navController.popBackStack()
                },
                onFinishListening = {
                    navController.navigate(Routes.COACH_PROCESSING)
                }
            )
        }

        // VORA COACH - PROCESSING
        composable(Routes.COACH_PROCESSING) {
            CoachProcessingScreen(
                onCancel = {
                    navController.popBackStack()
                },
                onFinishProcessing = {
                    navController.navigate(Routes.COACH_SPEAKING)
                }
            )
        }

        // VORA COACH - SPEAKING
        composable(Routes.COACH_SPEAKING) {
            CoachSpeakingScreen(
                onBack = {
                    navController.popBackStack()
                },
                onInterrupt = {
                    navController.navigate(Routes.COACH_ERROR) {
                        launchSingleTop = true
                    }
                },
                onOpenConversation = {
                    navController.navigate(Routes.COACH_CONVERSATION) {
                        launchSingleTop = true
                    }
                }
            )
        }

        // VORA COACH - CONVERSATION
        composable(Routes.COACH_CONVERSATION) {
            CoachConversationScreen(
                onBack = {
                    navController.popBackStack()
                },
                onPlayResponse = { _ ->
                    navController.navigate(Routes.COACH_SPEAKING)
                },
                onStartVoice = {
                    navController.navigate(Routes.COACH_LISTENING)
                }
            )
        }

        // VORA COACH - ERROR
        composable(Routes.COACH_ERROR) {
            CoachErrorScreen(
                onBack = {
                    navController.popBackStack()
                },
                onTryAgain = {
                    navController.navigate(Routes.COACH_LISTENING) {
                        popUpTo(Routes.COACH_ERROR) {
                            inclusive = true
                        }
                    }
                },
                onTypeInstead = {
                    navController.navigate(Routes.COACH_CONVERSATION) {
                        popUpTo(Routes.COACH_ERROR) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // WORKOUT
        composable(Routes.WORKOUT) {
            PlaceholderScreen(
                title = "Entrenamiento",
                links = arrayOf(
                    Routes.HOME to "Volver al inicio"
                ),
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }

        // NUTRITION
        composable(Routes.NUTRITION) {
            PlaceholderScreen(
                title = "Nutrición",
                links = arrayOf(
                    Routes.HOME to "Volver al inicio"
                ),
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }

        // PROGRESS - TODAY'S OVERVIEW
        composable(Routes.PROGRESS) {
            TodaysOverviewScreen(
                onStartWorkout = {
                    navController.navigate(Routes.WORKOUT)
                },
                onOpenDashboard = {
                    navController.navigate(Routes.PROGRESS_DASHBOARD)
                }
            )
        }

        // PROGRESS - TODAY
        composable(Routes.PROGRESS_TODAY) {
            TodaysOverviewScreen(
                onStartWorkout = {
                    navController.navigate(Routes.WORKOUT)
                },
                onOpenDashboard = {
                    navController.navigate(Routes.PROGRESS_DASHBOARD)
                }
            )
        }

        // PROGRESS - DASHBOARD
        composable(Routes.PROGRESS_DASHBOARD) {
            ProgressDashboardScreen(
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }

        // PROGRESS - WORKOUT
        composable(Routes.PROGRESS_WORKOUT) {
            WorkoutProgressScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // PROGRESS - NUTRITION
        composable(Routes.PROGRESS_NUTRITION) {
            NutritionProgressScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // PROGRESS - HEALTH
        composable(Routes.PROGRESS_HEALTH) {
            HealthTrendsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // PROGRESS - WEEKLY REVIEW
        composable(Routes.PROGRESS_WEEKLY) {
            WeeklyReviewScreen(
                onBack = {
                    navController.popBackStack()
                },
                onAskCoach = {
                    navController.navigate(Routes.COACH)
                }
            )
        }

        // PROFILE
        composable(Routes.PROFILE) {
            PlaceholderScreen(
                title = "Perfil",
                links = arrayOf(
                    Routes.HOME to "Volver al inicio"
                ),
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }
    }
}

@Composable
private fun PlaceholderScreen(
    title: String,
    links: Array<Pair<String, String>> = emptyArray(),
    onNavigate: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = title)

        links.forEach { (route, label) ->
            Button(
                onClick = {
                    onNavigate(route)
                }
            ) {
                Text(text = label)
            }
        }
    }
}
