package com.example.kurierapp

import com.example.kurierapp.profile.ProfileData
import com.example.kurierapp.send.SendData
import com.example.kurierapp.shipments.ShipmentData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

data class LoginRequest(val email: String, val password: String)
interface ApiInterface {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): ResponseBody

    @GET("user/@current")
    suspend fun getCurrentUser(): ProfileData

    @PATCH("user/@current")
    suspend fun updateCurrentUser(@Body profileData: ProfileData): Response<Unit>

    @POST("shipments")
    suspend fun sendShipment(@Body shipmentData: SendData): Response<Unit>

    @GET("user/@current/shipments")
    suspend fun getUserShipments(
        @Query("relation") relation: String = "recipient",
    ): List<ShipmentData>
}