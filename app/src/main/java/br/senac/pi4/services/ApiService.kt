package br.senac.pi4.services

import br.senac.pi4.models.grupos.Grupo
import br.senac.pi4.models.login.LoginRequest
import br.senac.pi4.models.login.LoginResponse
import br.senac.pi4.models.user.Aluno
import br.senac.pi4.models.user.AlunoRequest
import br.senac.pi4.models.user.ResponseAluno
import br.senac.pi4.models.user.UserSettings
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("api/auth/login_avalista")
    fun loginAvalista(@Body request: LoginRequest): Call<LoginResponse>

    @POST("api/auth/login_aluno")
    fun loginAluno(@Body request: LoginRequest): Call<LoginResponse>

    @GET("listarProjetos")
    fun listarProgetos(): Call<List<Grupo>>

    @GET("api/aluno/{userId}")
    fun getUserById(@Path("userId") userId: String): Call<UserSettings>

    @PUT("api/aluno/{userId}")
    fun updateUser(@Path("userId") userId: String, @Body userSettings: AlunoRequest): Call<ResponseAluno>

    @DELETE("api/aluno/{userId}")
    fun deleteUser(@Path("userId") userId: String): Call<Void>

}