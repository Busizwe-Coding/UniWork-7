package com.fitlog.app.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Defines the connection to FitLog's own REST API (see /backend), hosted on
 * Render and backed by MongoDB Atlas, as designed in the Part 1 document.
 */
interface ApiService {

    @POST("auth/register")
    suspend fun register(@Body body: RegisterRequest): AuthResponse

    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): AuthResponse

    @GET("users/me")
    suspend fun getMe(): UserDto

    @PATCH("users/me")
    suspend fun updateSettings(@Body body: UpdateSettingsRequest): UserDto

    @GET("workouts")
    suspend fun getWorkouts(@Query("since") since: Long = 0): List<WorkoutDto>

    @POST("workouts")
    suspend fun createWorkout(@Body body: WorkoutDto): WorkoutDto

    @GET("exercises")
    suspend fun getExercises(): List<ExerciseDto>

    @POST("devices/register")
    suspend fun registerDevice(@Body body: RegisterDeviceRequest)
}
