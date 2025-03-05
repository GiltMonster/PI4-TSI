package br.senac.pi4.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.senac.pi4.R
import br.senac.pi4.models.login.LoginRequest
import br.senac.pi4.models.login.LoginResponse
import br.senac.pi4.retroClient.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)

        val loginButton = findViewById<Button>(R.id.btnLogar)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val pass = passwordEditText.text.toString().trim()

            Log.d("LoginActivity", "Botão de login foi clicado")
            Log.d("LoginActivity", "Email digitado: $email, Senha: $pass")

            if (email.isEmpty() || pass.isEmpty()){
                Toast.makeText(this, "Preencher todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            makeLogin(email,pass)
        }

    }

    private fun makeLogin(email: String, password: String){
        val request = LoginRequest(email, password)
        val call = RetrofitClient.instance.login(request)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    val loginResponse = response.body()
                    Toast.makeText(applicationContext, "Bem vindo ${email}, ${loginResponse?.massage}", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(applicationContext, "Erro ao fazer login", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Falha na conexão: ${t.message}", Toast.LENGTH_LONG).show()
                Log.e("Erro", " ${t.message}")
            }
        })



    }

}