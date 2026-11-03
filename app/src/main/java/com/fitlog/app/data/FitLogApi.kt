package com.fitlog.app.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FitLogApi {
    @POST("/auth/verify") suspend fun verifyFirebaseToken(@Body body: Map<String, String>): Response<Map<String, Any>>
    @GET("/users/me") suspend fun getMe(): Response<Map<String, Any>>
    @PATCH("/users/me") suspend fun updateSettings(@Body body: Map<String, Any>): Response<Unit>
    @GET("/workouts") suspend fun getWorkouts(@Query("since") since: Long? = null): Response<List<Map<String, Any>>>
    @POST("/workouts") suspend fun createWorkout(@Body workout: Map<String, Any>): Response<Map<String, Any>>
    @PATCH("/workouts/{id}") suspend fun updateWorkout(@Path("id") id: String, @Body workout: Map<String, Any>): Response<Unit>
    @DELETE("/workouts/{id}") suspend fun deleteWorkout(@Path("id") id: String): Response<Unit>
    @GET("/exercises") suspend fun getExercises(): Response<List<Map<String, Any>>>
    @POST("/devices/register") suspend fun registerDevice(@Body body: Map<String, String>): Response<Unit>
}
