package br.senac.pi4.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.senac.pi4.R
import br.senac.pi4.models.login.LoginRequest
import br.senac.pi4.models.login.LoginResponse
import br.senac.pi4.retroClient.RetrofitClient
import br.senac.pi4.utils.toast
import com.google.android.material.switchmaterial.SwitchMaterial
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var switch_type_account: SwitchMaterial
    private lateinit var txt_login_desc: TextView

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
        switch_type_account = findViewById(R.id.switch_account)
        txt_login_desc = findViewById(R.id.txt_view_desc_login)
        val loginButton = findViewById<Button>(R.id.btnLogar)

        switch_type_account.setOnClickListener {
            if (switch_type_account.isChecked) {
                txt_login_desc.text = getString(R.string.txt_login_desc_aval)
                switch_type_account.text = getString(R.string.swt_avalista)
            } else {
                txt_login_desc.text = getString(R.string.txt_login_desc_aluno)
                switch_type_account.text = getString(R.string.swt_aluno)
            }
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val pass = passwordEditText.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty()) {
                toast("Por favor, preencha todos os campos", this)
                return@setOnClickListener
            }
            makeLogin(email, pass)
        }

    }

    private fun makeLogin(email: String, password: String) {
        val request = LoginRequest(email, password)
        val call: Call<LoginResponse>

        if (switch_type_account.isChecked) {
            call = RetrofitClient.instance.loginAvalista(request)
        } else {
            call = RetrofitClient.instance.loginAluno(request)
        }

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()

                    Log.d("Body", loginResponse.toString())
                    toast(loginResponse!!.message, applicationContext)

                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()

                } else if (response.code() == 401) {
                    toast("E-mail ou senha incorretos!", applicationContext)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                toast("Erro na conexão: ${t.message}", applicationContext)
                Log.e("Erro", " ${t.message}")
            }
        })
    }



}