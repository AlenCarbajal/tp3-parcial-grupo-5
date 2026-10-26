package ar.edu.ort.vora.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ar.edu.ort.vora.progress.ProgressDashboardScreen
import ar.edu.ort.vora.progress.ProgressScreen

private data class BottomTab(val route: String, val label: String, val icon: ImageVector)

// Íconos provisorios (core). Se cambian por los del Figma cuando estén los componentes.
private val bottomTabs = listOf(
    BottomTab(Routes.HOME, "Inicio", Icons.Filled.Home),
    BottomTab(Routes.WORKOUT, "Entrenamiento", Icons.Filled.Star),
    BottomTab(Routes.NUTRITION, "Nutrición", Icons.Filled.ShoppingCart),
    BottomTab(Routes.PROGRESS, "Progreso", Icons.Filled.DateRange),
    BottomTab(Routes.PROFILE, "Perfil", Icons.Filled.Person),
)

// Rutas donde la bottom bar se ve (según specs/02-screens.md).
private val routesWithBottomBar = setOf(
    Routes.HOME, Routes.WORKOUT, Routes.NUTRITION, Routes.PROGRESS, Routes.PROFILE,
    Routes.NOTIFICATIONS, Routes.NUTRITION_PHOTO, Routes.NUTRITION_REVIEW, Routes.NUTRITION_MEAL,
    Routes.PROGRESS_TODAY, Routes.PROGRESS_WORKOUT, Routes.PROGRESS_NUTRITION,
    Routes.PROGRESS_HEALTH, Routes.PROGRESS_WEEKLY,
)

@Composable
fun VoraNavHost(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = currentRoute in routesWithBottomBar
    val go: (String) -> Unit = { navController.navigate(it) }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomTabs.forEach { tab ->
                        NavigationBarItem(
                            selected = currentRoute?.startsWith(tab.route) == true,
                            onClick = {
                                navController.navigate(tab.route) {
                                    popUpTo(Routes.HOME) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(tab.icon, contentDescription = tab.label) },
                            label = { Text(tab.label) },
                        )
                    }
                }
            }
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.LOGIN,
            modifier = Modifier.padding(padding),
        ) {
            // Login
            composable(Routes.LOGIN) {
                PlaceholderScreen("Iniciar sesión", Routes.HOME to "Entrar", Routes.SIGNUP to "Crear cuenta") {
                    if (it == Routes.HOME) {
                        navController.navigate(Routes.HOME) { popUpTo(Routes.LOGIN) { inclusive = true } }
                    } else {
                        navController.navigate(it)
                    }
                }
            }
            composable(Routes.SIGNUP) {
                PlaceholderScreen("Crear cuenta", Routes.HOME to "Registrarme", Routes.LOGIN to "Iniciar sesión", onNavigate = go)
            }

            // Home
            composable(Routes.HOME) {
                PlaceholderScreen(
                    "Inicio",
                    Routes.NOTIFICATIONS to "Notificaciones",
                    Routes.COACH to "Start voice session",
                    Routes.workoutPrep("1") to "Start workout",
                    Routes.WORKOUT to "Start my workout",
                    Routes.NUTRITION_ADD to "Log breakfast",
                    Routes.PROGRESS_TODAY to "Today's Overview",
                    onNavigate = go,
                )
            }
            composable(Routes.NOTIFICATIONS) {
                PlaceholderScreen("Notificaciones", Routes.PROGRESS_WEEKLY to "Weekly review", onNavigate = go)
            }

            // Coach (asistente VORA)
            composable(Routes.COACH) {
                PlaceholderScreen("VORA Coach", Routes.COACH_LISTENING to "Tap to talk", Routes.COACH_CHAT to "Type instead", onNavigate = go)
            }
            composable(Routes.COACH_LISTENING) { PlaceholderScreen("Listening", Routes.COACH_PROCESSING to "Siguiente", onNavigate = go) }
            composable(Routes.COACH_PROCESSING) { PlaceholderScreen("Processing", Routes.COACH_SPEAKING to "Siguiente", onNavigate = go) }
            composable(Routes.COACH_SPEAKING) { PlaceholderScreen("VORA Speaking", Routes.COACH_CHAT to "Tap to Interrupt", onNavigate = go) }
            composable(Routes.COACH_CHAT) { PlaceholderScreen("Conversación") {} }
            composable(Routes.COACH_ERROR) {
                PlaceholderScreen("System Alert", Routes.COACH_LISTENING to "Try again", Routes.COACH_CHAT to "Type instead", onNavigate = go)
            }

            // Workout
            composable(Routes.WORKOUT) {
                PlaceholderScreen(
                    "Workouts",
                    Routes.WORKOUT_PLAN to "Plan",
                    Routes.workoutPrep("1") to "Start Workout",
                    Routes.workoutDetail("1") to "Details",
                    Routes.PROGRESS to "View progress",
                    onNavigate = go,
                )
            }
            composable(Routes.WORKOUT_PLAN) { PlaceholderScreen("Your Plan", Routes.workoutDetail("1") to "Tarjeta", onNavigate = go) }
            composable(Routes.WORKOUT_DETAIL) {
                PlaceholderScreen("Workout Overview", Routes.workoutPrep("1") to "Start Workout", Routes.workoutGuide("1", "1") to "Ejercicio", onNavigate = go)
            }
            composable(Routes.WORKOUT_PREP) { PlaceholderScreen("Get Ready", Routes.workoutActive("1") to "Start Workout", onNavigate = go) }
            composable(Routes.WORKOUT_ACTIVE) { PlaceholderScreen("Active", Routes.workoutDone("1") to "Finish Workout", onNavigate = go) }
            composable(Routes.WORKOUT_GUIDE) { PlaceholderScreen("Exercise Guide") {} }
            composable(Routes.WORKOUT_DONE) { PlaceholderScreen("Completed", Routes.HOME to "Done", onNavigate = go) }

            // Nutrition
            composable(Routes.NUTRITION) {
                PlaceholderScreen(
                    "Nutrition",
                    Routes.NUTRITION_MEALS to "Comidas",
                    Routes.NUTRITION_VOICE to "Voice",
                    Routes.NUTRITION_PHOTO to "Photo",
                    Routes.NUTRITION_MANUAL to "Search",
                    onNavigate = go,
                )
            }
            composable(Routes.NUTRITION_MEALS) {
                PlaceholderScreen("Today's Meals", Routes.NUTRITION_ADD to "Add Dinner", Routes.nutritionMeal("1") to "Detalle", onNavigate = go)
            }
            composable(Routes.NUTRITION_ADD) {
                PlaceholderScreen("Add Food", Routes.NUTRITION_VOICE to "Voice", Routes.NUTRITION_PHOTO to "Photo", Routes.NUTRITION_MANUAL to "Manual", onNavigate = go)
            }
            composable(Routes.NUTRITION_VOICE) {
                PlaceholderScreen("Voice Logging", Routes.NUTRITION_ANALYZING to "Done Speaking", Routes.NUTRITION_MANUAL to "Type instead", onNavigate = go)
            }
            composable(Routes.NUTRITION_ANALYZING) { PlaceholderScreen("Analyzing Meal", Routes.NUTRITION_REVIEW to "Siguiente", onNavigate = go) }
            composable(Routes.NUTRITION_PHOTO) { PlaceholderScreen("Analyse Meal", Routes.NUTRITION_REVIEW to "Review & Add", onNavigate = go) }
            composable(Routes.NUTRITION_REVIEW) { PlaceholderScreen("Review Meal", Routes.NUTRITION_MEALS to "Add to Lunch", onNavigate = go) }
            composable(Routes.NUTRITION_MEAL) { PlaceholderScreen("Lunch") {} }
            composable(Routes.NUTRITION_MANUAL) { PlaceholderScreen("Registro manual", Routes.NUTRITION_MEALS to "Guardar", onNavigate = go) }

            // Progress (pantallas reales de Lisandro)
            composable(Routes.PROGRESS) { ProgressDashboardScreen() }
            composable(Routes.PROGRESS_TODAY) { ProgressScreen() }
            composable(Routes.PROGRESS_WORKOUT) { PlaceholderScreen("Workout progress") {} }
            composable(Routes.PROGRESS_NUTRITION) { PlaceholderScreen("Nutrition progress") {} }
            composable(Routes.PROGRESS_HEALTH) { PlaceholderScreen("Health trends") {} }
            composable(Routes.PROGRESS_WEEKLY) { PlaceholderScreen("Your weekly review", Routes.COACH to "Ask VORA", onNavigate = go) }

            // Profile
            composable(Routes.PROFILE) {
                PlaceholderScreen(
                    "Perfil",
                    Routes.PROFILE_EDIT to "Edit",
                    Routes.PROFILE_GOALS to "Goals",
                    Routes.PROFILE_NUTRITION to "Nutrition prefs",
                    Routes.PROFILE_SETTINGS to "Settings",
                    onNavigate = go,
                )
            }
            composable(Routes.PROFILE_EDIT) { PlaceholderScreen("Edit profile") {} }
            composable(Routes.PROFILE_GOALS) { PlaceholderScreen("Your goals") {} }
            composable(Routes.PROFILE_NUTRITION) { PlaceholderScreen("Nutrition preferences") {} }
            composable(Routes.PROFILE_VOICE) { PlaceholderScreen("VORA Coach (Voz e IA)") {} }
            composable(Routes.PROFILE_SETTINGS) {
                PlaceholderScreen("Settings", Routes.PROFILE_VOICE to "Voz e IA", Routes.LOGIN to "Log out") {
                    if (it == Routes.LOGIN) {
                        navController.navigate(Routes.LOGIN) { popUpTo(navController.graph.id) { inclusive = true } }
                    } else {
                        navController.navigate(it)
                    }
                }
            }
        }
    }
}

// Pantalla temporal: se reemplaza por la real de cada área.
@Composable
private fun PlaceholderScreen(
    title: String,
    vararg links: Pair<String, String>,
    onNavigate: (String) -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        Text(title)
        links.forEach { (route, label) ->
            Button(onClick = { onNavigate(route) }) { Text(label) }
        }
    }
}