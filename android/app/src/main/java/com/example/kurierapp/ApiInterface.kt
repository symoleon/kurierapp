package com.example.kurierapp

import com.example.kurierapp.profile.ProfileData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

data class LoginRequest(val email: String, val password: String)
interface ApiInterface {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): ResponseBody

    @GET("user/@current")
    suspend fun getCurrentUser(): ProfileData

    @PATCH("user/@current")
    suspend fun updateCurrentUser(@Body profileData: ProfileData): Response<Unit>
}