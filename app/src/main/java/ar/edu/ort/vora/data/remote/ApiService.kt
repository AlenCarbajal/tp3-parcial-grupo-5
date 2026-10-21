package ar.edu.ort.vora.data.remote

import ar.edu.ort.vora.data.remote.dto.WorkoutResponseDto
import ar.edu.ort.vora.data.remote.dto.WorkoutDetailResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("v1/workouts")
    suspend fun getWorkouts(): WorkoutResponseDto

    @GET("v1/workouts/{workoutId}")
    suspend fun getWorkoutById(
        @Path("workoutId") workoutId: String
    ): WorkoutDetailResponseDto
}