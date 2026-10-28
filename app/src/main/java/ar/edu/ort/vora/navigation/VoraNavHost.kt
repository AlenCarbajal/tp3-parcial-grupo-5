package ar.edu.ort.vora.navigation

import androidx.activity.compose.LocalActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.edu.ort.vora.ui.components.VoraBottomBar
import ar.edu.ort.vora.ui.workout.ExerciseGuideScreen
import ar.edu.ort.vora.ui.workout.WorkoutActiveScreen
import ar.edu.ort.vora.ui.workout.WorkoutCatalogScreen
import ar.edu.ort.vora.ui.workout.WorkoutCompletedScreen
import ar.edu.ort.vora.ui.workout.WorkoutDetailScreen
import ar.edu.ort.vora.ui.workout.WorkoutPlanScreen
import ar.edu.ort.vora.ui.workout.WorkoutPrepScreen
import ar.edu.ort.vora.ui.workout.WorkoutViewModel
import ar.edu.ort.vora.ui.nutrition.ManualMealScreen
import ar.edu.ort.vora.ui.nutrition.MealDetailScreen
import ar.edu.ort.vora.ui.nutrition.MealsScreen

// Rutas quemadas como texto para evitar errores si no están en tu Routes.kt
private const val ROUTE_COACH = "coach"
private const val ROUTE_COACH_LISTENING = "coach_listening"
private const val ROUTE_COACH_PROCESSING = "coach_processing"
private const val ROUTE_COACH_SPEAKING = "coach_speaking"
private const val ROUTE_COACH_CHAT = "coach_chat"
private const val ROUTE_COACH_ERROR = "coach_error"

private const val ROUTE_PROFILE = "profile"
private const val ROUTE_PROFILE_EDIT = "profile_edit"
private const val ROUTE_PROFILE_GOALS = "profile_goals"
private const val ROUTE_PROFILE_NUTRITION = "profile_nutrition"
private const val ROUTE_PROFILE_VOICE = "profile_voice"
private const val ROUTE_PROFILE_SETTINGS = "profile_settings"

private const val ROUTE_PROGRESS = "progress"
private const val ROUTE_PROGRESS_TODAY = "progress_today"
private const val ROUTE_PROGRESS_WORKOUT = "progress_workout"
private const val ROUTE_PROGRESS_NUTRITION = "progress_nutrition"
private const val ROUTE_PROGRESS_HEALTH = "progress_health"
private const val ROUTE_PROGRESS_WEEKLY = "progress_weekly"

private const val ROUTE_NUTRITION = "nutrition"
private const val ROUTE_NUTRITION_MEALS = "nutrition_meals"
private const val ROUTE_NUTRITION_ADD = "nutrition_add"
private const val ROUTE_NUTRITION_VOICE = "nutrition_voice"
private const val ROUTE_NUTRITION_ANALYZING = "nutrition_analyzing"
private const val ROUTE_NUTRITION_PHOTO = "nutrition_photo"
private const val ROUTE_NUTRITION_REVIEW = "nutrition_review"
private const val ROUTE_NUTRITION_MANUAL = "nutrition_manual"

private val bottomTabs = listOf(Routes.HOME, Routes.WORKOUT, ROUTE_NUTRITION, ROUTE_PROGRESS, ROUTE_PROFILE)

private val edgeToEdgeRoutes = setOf(
    Routes.LOGIN, Routes.SIGNUP,
    ROUTE_COACH, ROUTE_COACH_LISTENING, ROUTE_COACH_PROCESSING, ROUTE_COACH_SPEAKING, ROUTE_COACH_CHAT, ROUTE_COACH_ERROR,
)

private val darkRoutes = setOf(ROUTE_COACH_LISTENING, ROUTE_COACH_PROCESSING, ROUTE_COACH_SPEAKING)

private val routesWithBottomBar = setOf(
    Routes.HOME, Routes.WORKOUT, ROUTE_NUTRITION, ROUTE_PROGRESS, ROUTE_PROFILE,
    Routes.NOTIFICATIONS, ROUTE_NUTRITION_MEALS, ROUTE_NUTRITION_PHOTO,
    ROUTE_NUTRITION_REVIEW, ROUTE_NUTRITION_ADD, ROUTE_NUTRITION_VOICE,
    ROUTE_NUTRITION_ANALYZING, ROUTE_NUTRITION_MANUAL, ROUTE_PROGRESS_TODAY,
    ROUTE_PROGRESS_WORKOUT, ROUTE_PROGRESS_NUTRITION, ROUTE_PROGRESS_HEALTH, ROUTE_PROGRESS_WEEKLY
)

@Composable
fun VoraNavHost(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = currentRoute in routesWithBottomBar

    val window = LocalActivity.current?.window
    val view = LocalView.current
    val darkStatusBar = currentRoute in darkRoutes
    SideEffect { window?.let { WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = !darkStatusBar } }

    val selectedTab = if (currentRoute == Routes.NOTIFICATIONS) 0 else bottomTabs.indexOfFirst { currentRoute?.startsWith(it) == true }

    val goTab: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(Routes.HOME) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
    val back: () -> Unit = { navController.popBackStack() }
    val go: (String) -> Unit = { navController.navigate(it) }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                VoraBottomBar(
                    selectedIndex = selectedTab.coerceAtLeast(0),
                    onSelect = { goTab(bottomTabs[it]) }
                )
            }
        },
        contentWindowInsets = if (currentRoute in edgeToEdgeRoutes) WindowInsets(0) else ScaffoldDefaults.contentWindowInsets,
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.LOGIN,
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding),
        ) {
            composable(Routes.LOGIN) {
                PlaceholderScreen("Iniciar sesión", Routes.HOME to "Entrar", Routes.SIGNUP to "Crear cuenta") { route ->
                    if (route == Routes.HOME) navController.navigate(Routes.HOME) { popUpTo(Routes.LOGIN) { inclusive = true } }
                    else navController.navigate(route)
                }
            }
            composable(Routes.SIGNUP) {
                PlaceholderScreen("Crear cuenta", Routes.HOME to "Registrarme", Routes.LOGIN to "Iniciar sesión") { route -> navController.navigate(route) }
            }
            composable(Routes.HOME) {
                PlaceholderScreen("Inicio", Routes.NOTIFICATIONS to "Notificaciones", ROUTE_COACH to "Asistente VORA", Routes.WORKOUT to "Entrenamiento") { route -> navController.navigate(route) }
            }
            composable(Routes.NOTIFICATIONS) {
                PlaceholderScreen("Notificaciones")
            }

            // --- COACH ---
            composable(ROUTE_COACH) { PlaceholderScreen("Asistente VORA")  }
            composable(ROUTE_COACH_LISTENING) { PlaceholderScreen("Escuchando") }
            composable(ROUTE_COACH_PROCESSING) { PlaceholderScreen("Procesando") }
            composable(ROUTE_COACH_SPEAKING) { PlaceholderScreen("Hablando") }
            composable(ROUTE_COACH_CHAT) { PlaceholderScreen("Conversación") }
            composable(ROUTE_COACH_ERROR) { PlaceholderScreen("Error") }

            // --- ENTRENAMIENTO (Tus pantallas reales) ---
            composable(Routes.WORKOUT) {
                WorkoutCatalogScreen(
                    onWorkoutClick = { workoutId -> navController.navigate(Routes.workoutDetail(workoutId)) },
                    onPlanClick = { navController.navigate(Routes.WORKOUT_PLAN) }
                )
            }
            composable(Routes.WORKOUT_PLAN) {
                val workoutEntry = rememberWorkoutEntry(navController)
                val viewModel = hiltViewModel<WorkoutViewModel>(workoutEntry)
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                WorkoutPlanScreen(
                    workouts = state.workouts,
                    onWorkoutClick = { workoutId -> navController.navigate(Routes.workoutDetail(workoutId)) },
                    onStartWorkout = { workoutId -> navController.navigate(Routes.workoutPrep(workoutId)) },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(route = Routes.WORKOUT_DETAIL, arguments = listOf(navArgument("workoutId") { type = NavType.StringType })) { entry ->
                val workoutId = entry.arguments?.getString("workoutId").orEmpty()
                val viewModel = hiltViewModel<WorkoutViewModel>(rememberWorkoutEntry(navController))
                WorkoutDetailScreen(
                    workoutId = workoutId,
                    onBack = { navController.popBackStack() },
                    onStartWorkout = { navController.navigate(Routes.workoutPrep(workoutId)) },
                    onExerciseClick = { exerciseId -> navController.navigate(Routes.workoutGuide(workoutId, exerciseId)) },
                    viewModel = viewModel
                )
            }
            composable(Routes.NUTRITION_MEALS) {
                MealsScreen(
                    onBack = back,
                    onAdd = { go(Routes.NUTRITION_ADD) },
                    onMeal = { id -> go(Routes.nutritionMeal(id.toString())) },
                )
            }
            composable(route = Routes.WORKOUT_PREP, arguments = listOf(navArgument("workoutId") { type = NavType.StringType })) { entry ->
                val workoutId = entry.arguments?.getString("workoutId").orEmpty()
                val viewModel = hiltViewModel<WorkoutViewModel>(rememberWorkoutEntry(navController))
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                LaunchedEffect(workoutId) { if (state.selectedWorkout?.id != workoutId) viewModel.loadWorkoutDetail(workoutId) }
                WorkoutPrepScreen(
                    workout = state.selectedWorkout?.takeIf { it.id == workoutId },
                    onStartWorkout = { navController.navigate(Routes.workoutActive(workoutId)) },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(route = Routes.WORKOUT_ACTIVE, arguments = listOf(navArgument("workoutId") { type = NavType.StringType })) { entry ->
                val workoutId = entry.arguments?.getString("workoutId").orEmpty()
                val viewModel = hiltViewModel<WorkoutViewModel>(rememberWorkoutEntry(navController))
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                LaunchedEffect(workoutId) { if (state.selectedWorkout?.id != workoutId) viewModel.loadWorkoutDetail(workoutId) }
                WorkoutActiveScreen(
                    workout = state.selectedWorkout?.takeIf { it.id == workoutId },
                    onGuideClick = { exerciseId -> navController.navigate(Routes.workoutGuide(workoutId, exerciseId)) },
                    onFinish = { navController.navigate(Routes.workoutDone(workoutId)) }
                )
            }
            composable(route = Routes.WORKOUT_GUIDE, arguments = listOf(navArgument("workoutId") { type = NavType.StringType }, navArgument("exerciseId") { type = NavType.StringType })) { entry ->
                val workoutId = entry.arguments?.getString("workoutId").orEmpty()
                val exerciseId = entry.arguments?.getString("exerciseId").orEmpty()
                val viewModel = hiltViewModel<WorkoutViewModel>(rememberWorkoutEntry(navController))
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                LaunchedEffect(workoutId) { if (state.selectedWorkout?.id != workoutId) viewModel.loadWorkoutDetail(workoutId) }
                ExerciseGuideScreen(
                    workout = state.selectedWorkout?.takeIf { it.id == workoutId },
                    exerciseId = exerciseId,
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Routes.NUTRITION_ANALYZING) { PlaceholderScreen("Analyzing Meal", Routes.NUTRITION_REVIEW to "Siguiente", onNavigate = go) }
            composable(Routes.NUTRITION_PHOTO) { PlaceholderScreen("Analyse Meal", Routes.NUTRITION_REVIEW to "Review & Add", onNavigate = go) }
            composable(Routes.NUTRITION_REVIEW) { PlaceholderScreen("Review Meal", Routes.NUTRITION_MEALS to "Add to Lunch", onNavigate = go) }
            composable(
                Routes.NUTRITION_MEAL,
                arguments = listOf(navArgument("id") { type = NavType.IntType }),
            ) {
                MealDetailScreen(onBack = back)
            }
            composable(Routes.NUTRITION_MANUAL) {
                ManualMealScreen(
                    onBack = back,
                    onSaved = {
                        // Si ya había una lista debajo se vuelve a ella; si no, el formulario se reemplaza por la lista
                        if (!navController.popBackStack(Routes.NUTRITION_MEALS, inclusive = false)) {
                            navController.navigate(Routes.NUTRITION_MEALS) {
                                popUpTo(Routes.NUTRITION_MANUAL) { inclusive = true }
                            }
                        }
                    },
                )
            }
            composable(route = Routes.WORKOUT_DONE, arguments = listOf(navArgument("workoutId") { type = NavType.StringType })) { entry ->
                val workoutId = entry.arguments?.getString("workoutId").orEmpty()
                val viewModel = hiltViewModel<WorkoutViewModel>(rememberWorkoutEntry(navController))
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                LaunchedEffect(workoutId) { if (state.selectedWorkout?.id != workoutId) viewModel.loadWorkoutDetail(workoutId) }
                WorkoutCompletedScreen(
                    workout = state.selectedWorkout?.takeIf { it.id == workoutId },
                    onHome = { navController.navigate(Routes.HOME) { popUpTo(Routes.HOME) { inclusive = true }; launchSingleTop = true } },
                    onBackToWorkout = { navController.navigate(Routes.workoutDetail(workoutId)) { popUpTo(Routes.WORKOUT) { inclusive = false }; launchSingleTop = true } }
                )
            }

            // --- NUTRICIÓN ---
            composable(ROUTE_NUTRITION) { PlaceholderScreen("Nutrición") }
            composable(ROUTE_NUTRITION_MEALS) { PlaceholderScreen("Comidas") }
            composable(ROUTE_NUTRITION_ADD) { PlaceholderScreen("Agregar") }
            composable(ROUTE_NUTRITION_VOICE) { PlaceholderScreen("Voz") }
            composable(ROUTE_NUTRITION_ANALYZING) { PlaceholderScreen("Analizando") }
            composable(ROUTE_NUTRITION_PHOTO) { PlaceholderScreen("Foto") }
            composable(ROUTE_NUTRITION_REVIEW) { PlaceholderScreen("Review") }
            composable(ROUTE_NUTRITION_MANUAL) { PlaceholderScreen("Manual") }

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
    onNavigate: (String) -> Unit = {},
) {
    Column(Modifier.fillMaxSize()) {
        Text(title)
        links.forEach { (route, label) ->
            Button(onClick = { onNavigate(route) }) { Text(label) }
        }
    }
}

@Composable
private fun rememberWorkoutEntry(
    navController: NavHostController
): androidx.navigation.NavBackStackEntry {
    val entry = navController.currentBackStackEntry
    return androidx.compose.runtime.remember(entry) {
        navController.getBackStackEntry(Routes.WORKOUT)
    }
}