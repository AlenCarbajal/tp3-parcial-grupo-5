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
import ar.edu.ort.vora.domain.model.Workout
import ar.edu.ort.vora.progress.HealthTrendsScreen
import ar.edu.ort.vora.progress.NutritionProgressScreen
import ar.edu.ort.vora.progress.ProgressDashboardScreen
import ar.edu.ort.vora.progress.TodaysOverviewScreen
import ar.edu.ort.vora.progress.WeeklyReviewScreen
import ar.edu.ort.vora.progress.WorkoutProgressScreen
import ar.edu.ort.vora.ui.auth.SignInScreen
import ar.edu.ort.vora.ui.auth.SignUpScreen
import ar.edu.ort.vora.ui.coach.CoachChatScreen
import ar.edu.ort.vora.ui.coach.CoachErrorScreen
import ar.edu.ort.vora.ui.coach.CoachIdleScreen
import ar.edu.ort.vora.ui.coach.CoachListeningScreen
import ar.edu.ort.vora.ui.coach.CoachProcessingScreen
import ar.edu.ort.vora.ui.coach.CoachSpeakingScreen
import ar.edu.ort.vora.ui.components.VoraBottomBar
import ar.edu.ort.vora.ui.home.HomeScreen
import ar.edu.ort.vora.ui.home.NotificationsScreen
import ar.edu.ort.vora.ui.nutrition.ManualMealScreen
import ar.edu.ort.vora.ui.nutrition.MealDetailScreen
import ar.edu.ort.vora.ui.nutrition.MealsScreen
import ar.edu.ort.vora.ui.profile.ProfileEditScreen
import ar.edu.ort.vora.ui.profile.ProfileGoalsScreen
import ar.edu.ort.vora.ui.profile.ProfileNutritionScreen
import ar.edu.ort.vora.ui.profile.ProfileScreen
import ar.edu.ort.vora.ui.profile.ProfileSettingsScreen
import ar.edu.ort.vora.ui.profile.ProfileVoiceScreen
import ar.edu.ort.vora.ui.workout.ExerciseGuideScreen
import ar.edu.ort.vora.ui.workout.WorkoutActiveScreen
import ar.edu.ort.vora.ui.workout.WorkoutCatalogScreen
import ar.edu.ort.vora.ui.workout.WorkoutCompletedScreen
import ar.edu.ort.vora.ui.workout.WorkoutDetailScreen
import ar.edu.ort.vora.ui.workout.WorkoutPlanScreen
import ar.edu.ort.vora.ui.workout.WorkoutPrepScreen
import ar.edu.ort.vora.ui.workout.WorkoutViewModel

// Destinos de la barra inferior, en el orden de VoraBottomBarItems (íconos y textos de Figma).
private val bottomTabs = listOf(Routes.HOME, Routes.WORKOUT, Routes.NUTRITION, Routes.PROGRESS, Routes.PROFILE)

// Acceso y Coach: dibujan su fondo detrás de la barra de estado y manejan sus propios márgenes del sistema.
private val edgeToEdgeRoutes = setOf(
    Routes.LOGIN, Routes.SIGNUP,
    Routes.COACH, Routes.COACH_LISTENING, Routes.COACH_PROCESSING, Routes.COACH_SPEAKING, Routes.COACH_CHAT, Routes.COACH_ERROR,
)

private val darkRoutes = setOf(Routes.COACH_LISTENING, Routes.COACH_PROCESSING, Routes.COACH_SPEAKING)

// Rutas donde la bottom bar se ve (según specs/02-screens.md).
private val routesWithBottomBar = setOf(
    Routes.HOME, Routes.WORKOUT, Routes.NUTRITION, Routes.PROGRESS, Routes.PROFILE,
    Routes.NOTIFICATIONS, Routes.NUTRITION_PHOTO, Routes.NUTRITION_REVIEW, Routes.NUTRITION_MEAL,
    Routes.PROGRESS_TODAY, Routes.PROGRESS_WORKOUT, Routes.PROGRESS_NUTRITION,
    Routes.PROGRESS_HEALTH, Routes.PROGRESS_WEEKLY,
)

@Composable
fun VoraNavHost(navController: NavHostController = rememberNavController()) {
    // Un único WorkoutViewModel para todo el flujo: el entrenamiento elegido en el detalle
    // sigue disponible en Prep, Active, Guide y Completed (NAV04).
    val workoutViewModel: WorkoutViewModel = hiltViewModel()
    val workoutState by workoutViewModel.uiState.collectAsStateWithLifecycle()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = currentRoute in routesWithBottomBar
    // Las pantallas verdes del Coach van con íconos claros en la barra de estado; el resto, oscuros.
    val window = LocalActivity.current?.window
    val view = LocalView.current
    val darkStatusBar = currentRoute in darkRoutes
    SideEffect { window?.let { WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = !darkStatusBar } }
    // Notifications no tiene pestaña propia: en Figma queda marcado Home.
    val selectedTab = if (currentRoute == Routes.NOTIFICATIONS) 0 else bottomTabs.indexOfFirst { currentRoute?.startsWith(it) == true }
    val go: (String) -> Unit = { navController.navigate(it) }
    val goTab: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(Routes.HOME) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
    // Al entrar se saca el acceso de la pila: desde Home, atrás cierra la app en vez de volver al login.
    val enterApp: () -> Unit = { navController.navigate(Routes.HOME) { popUpTo(Routes.LOGIN) { inclusive = true } } }
    val back: () -> Unit = { navController.popBackStack() }
    // Cada paso del Coach reemplaza al anterior: atrás vuelve a la pantalla inicial del asistente.
    val coachStep: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(Routes.COACH)
            launchSingleTop = true
        }
    }
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val goBack: () -> Unit = { backDispatcher?.onBackPressed() }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                VoraBottomBar(selectedIndex = selectedTab, onSelect = { goTab(bottomTabs[it]) })
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
            // Login
            composable(Routes.LOGIN) {
                SignInScreen(onSignIn = enterApp, onSignUp = { navController.navigate(Routes.SIGNUP) { launchSingleTop = true } }, onBack = goBack)
            }
            composable(Routes.SIGNUP) {
                SignUpScreen(
                    onSignUp = enterApp,
                    onSignIn = { navController.navigate(Routes.LOGIN) { popUpTo(Routes.LOGIN) { inclusive = true } } },
                    onBack = goBack,
                )
            }

            // Home
            composable(Routes.HOME) {
                HomeScreen(
                    onNotifications = { go(Routes.NOTIFICATIONS) },
                    onVoiceSession = { go(Routes.COACH) },
                    onStartWorkout = { go(Routes.workoutPrep("w01")) },
                    onLogMeal = { go(Routes.NUTRITION_MANUAL) },
                    onTodayOverview = { go(Routes.PROGRESS_TODAY) },
                )
            }
            composable(Routes.NOTIFICATIONS) {
                NotificationsScreen(
                    onWeeklyReview = { go(Routes.PROGRESS_WEEKLY) },
                    onWorkout = { goTab(Routes.WORKOUT) },
                    onNutrition = { goTab(Routes.NUTRITION) },
                    onProgress = { goTab(Routes.PROGRESS) },
                )
            }

            // Coach (asistente VORA)
            composable(Routes.COACH) {
                CoachIdleScreen(onBack = back, onTalk = { go(Routes.COACH_LISTENING) }, onTypeInstead = { go(Routes.COACH_CHAT) })
            }
            composable(Routes.COACH_LISTENING) {
                CoachListeningScreen(onBack = back, onFinished = { heard -> coachStep(if (heard) Routes.COACH_PROCESSING else Routes.COACH_ERROR) })
            }
            composable(Routes.COACH_PROCESSING) {
                CoachProcessingScreen(onBack = back, onAnswer = { coachStep(Routes.COACH_SPEAKING) })
            }
            composable(Routes.COACH_SPEAKING) {
                CoachSpeakingScreen(onBack = back, onInterrupt = { coachStep(Routes.COACH_CHAT) })
            }
            composable(Routes.COACH_CHAT) {
                CoachChatScreen(onBack = back, onListen = { go(Routes.COACH_SPEAKING) })
            }
            composable(Routes.COACH_ERROR) {
                CoachErrorScreen(
                    onBack = back,
                    onTryAgain = { coachStep(Routes.COACH_LISTENING) },
                    onTypeInstead = { coachStep(Routes.COACH_CHAT) },
                )
            }

            // Workout (RF01, RF02, NAV04)
            composable(Routes.WORKOUT) {
                WorkoutCatalogScreen(
                    onWorkoutClick = { go(Routes.workoutDetail(it)) },
                    onPlanClick = { go(Routes.WORKOUT_PLAN) },
                    viewModel = workoutViewModel,
                )
            }
            composable(Routes.WORKOUT_PLAN) {
                WorkoutPlanScreen(
                    workouts = workoutState.workouts,
                    onWorkoutClick = { go(Routes.workoutDetail(it)) },
                    onStartWorkout = { go(Routes.workoutPrep(it)) },
                    onBack = back,
                )
            }
            composable(Routes.WORKOUT_DETAIL) { entry ->
                val id = entry.arguments?.getString("workoutId").orEmpty()
                WorkoutDetailScreen(
                    workoutId = id,
                    onBack = back,
                    onStartWorkout = { go(Routes.workoutPrep(id)) },
                    onExerciseClick = { go(Routes.workoutGuide(id, it)) },
                    viewModel = workoutViewModel,
                )
            }
            composable(Routes.WORKOUT_PREP) { entry ->
                val id = entry.arguments?.getString("workoutId").orEmpty()
                WorkoutPrepScreen(
                    workout = workoutFor(id, workoutViewModel),
                    onStartWorkout = { go(Routes.workoutActive(id)) },
                    onBack = back,
                )
            }
            composable(Routes.WORKOUT_ACTIVE) { entry ->
                val id = entry.arguments?.getString("workoutId").orEmpty()
                WorkoutActiveScreen(
                    workout = workoutFor(id, workoutViewModel),
                    onGuideClick = { go(Routes.workoutGuide(id, it)) },
                    onFinish = { go(Routes.workoutDone(id)) },
                )
            }
            composable(Routes.WORKOUT_GUIDE) { entry ->
                val id = entry.arguments?.getString("workoutId").orEmpty()
                val exerciseId = entry.arguments?.getString("exerciseId").orEmpty()
                ExerciseGuideScreen(
                    workout = workoutFor(id, workoutViewModel),
                    exerciseId = exerciseId,
                    onBack = back,
                )
            }
            composable(Routes.WORKOUT_DONE) { entry ->
                val id = entry.arguments?.getString("workoutId").orEmpty()
                WorkoutCompletedScreen(
                    workout = workoutFor(id, workoutViewModel),
                    onHome = { goTab(Routes.HOME) },
                    // Si el detalle no está en la pila (se llegó desde Home), se abre.
                    onBackToWorkout = {
                        if (!navController.popBackStack(Routes.WORKOUT_DETAIL, inclusive = false)) {
                            go(Routes.workoutDetail(id))
                        }
                    },
                )
            }

            // Nutrition (RF03, RF04). Las pantallas demo (voz, análisis, foto, revisión) siguen pendientes (T33).
            composable(Routes.NUTRITION) {
                MealsScreen(
                    onBack = back,
                    onAdd = { go(Routes.NUTRITION_MANUAL) },
                    onMeal = { go(Routes.nutritionMeal(it.toString())) },
                )
            }
            composable(Routes.NUTRITION_MEALS) {
                MealsScreen(
                    onBack = back,
                    onAdd = { go(Routes.NUTRITION_MANUAL) },
                    onMeal = { go(Routes.nutritionMeal(it.toString())) },
                )
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
            composable(
                Routes.NUTRITION_MEAL,
                arguments = listOf(navArgument("mealId") { type = NavType.IntType }),
            ) {
                MealDetailScreen(onBack = back)
            }
            composable(Routes.NUTRITION_MANUAL) {
                ManualMealScreen(onBack = back, onSaved = back)
            }

            // Progress (pantallas reales de Lisandro)
            composable(Routes.PROGRESS) { ProgressDashboardScreen(onNavigate = go) }
            composable(Routes.PROGRESS_TODAY) {
                TodaysOverviewScreen(
                    onStartWorkout = { goTab(Routes.WORKOUT) },
                    // El dashboard de Progress es la pestaña Progress (no existe una ruta aparte).
                    onOpenDashboard = { goTab(Routes.PROGRESS) }
                )
            }
            composable(Routes.PROGRESS_WORKOUT) { WorkoutProgressScreen(onBack = back) }
            composable(Routes.PROGRESS_NUTRITION) { NutritionProgressScreen(onBack = back) }
            composable(Routes.PROGRESS_HEALTH) { HealthTrendsScreen(onBack = back) }
            composable(Routes.PROGRESS_WEEKLY) { WeeklyReviewScreen(onBack = back, onAskCoach = { go(Routes.COACH) }) }

            // Profile
            composable(Routes.PROFILE) {
                ProfileScreen(
                    onEdit = { go(Routes.PROFILE_EDIT) },
                    onGoals = { go(Routes.PROFILE_GOALS) },
                    onNutrition = { go(Routes.PROFILE_NUTRITION) },
                    onSettings = { go(Routes.PROFILE_SETTINGS) },
                )
            }
            composable(Routes.PROFILE_EDIT) {
                ProfileEditScreen(onBack = { navController.popBackStack() }, onSave = { navController.popBackStack() })
            }
            composable(Routes.PROFILE_GOALS) {
                ProfileGoalsScreen(onBack = { navController.popBackStack() }, onSave = { navController.popBackStack() })
            }
            composable(Routes.PROFILE_NUTRITION) {
                ProfileNutritionScreen(onBack = { navController.popBackStack() }, onSave = { navController.popBackStack() })
            }
            composable(Routes.PROFILE_VOICE) {
                ProfileVoiceScreen(onBack = { navController.popBackStack() })
            }
            composable(Routes.PROFILE_SETTINGS) {
                ProfileSettingsScreen(
                    onBack = { navController.popBackStack() },
                    onVoice = { navController.navigate(Routes.PROFILE_VOICE) },
                    onLogout = {
                        navController.navigate(Routes.LOGIN) { popUpTo(navController.graph.id) { inclusive = true } }
                    },
                )
            }
        }
    }
}

// Devuelve el entrenamiento `id` y lo pide al ViewModel si todavía no es el seleccionado
// (por ejemplo, al entrar directo desde Home sin pasar por el detalle).
@Composable
private fun workoutFor(id: String, viewModel: WorkoutViewModel): Workout? {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(id) {
        if (state.selectedWorkout?.id != id) viewModel.loadWorkoutDetail(id)
    }
    return state.selectedWorkout?.takeIf { it.id == id }
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