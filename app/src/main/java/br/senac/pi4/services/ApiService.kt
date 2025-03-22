package br.senac.pi4.services

import br.senac.pi4.models.grupos.Grupo
import br.senac.pi4.models.login.LoginRequest
import br.senac.pi4.models.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/login_avalista")
    fun loginAvalista(@Body request: LoginRequest): Call<LoginResponse>

    @POST("api/auth/login_aluno")
    fun loginAluno(@Body request: LoginRequest): Call<LoginResponse>

    @GET("listarProjetos")
    fun listarProgetos(): Call<List<Grupo>>
}