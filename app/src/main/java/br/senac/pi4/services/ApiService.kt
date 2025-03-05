package br.senac.pi4.services

import br.senac.pi4.models.login.LoginRequest
import br.senac.pi4.models.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("logar")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}