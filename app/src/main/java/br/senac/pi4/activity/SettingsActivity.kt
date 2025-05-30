package br.senac.pi4.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.senac.pi4.R
import br.senac.pi4.databinding.ActivitySettingsBinding
import br.senac.pi4.fragment.BottomNavigationFragment
import br.senac.pi4.models.user.Aluno
import br.senac.pi4.retroClient.RetrofitClient
import br.senac.pi4.utils.toast
import com.bumptech.glide.Glide
import androidx.core.content.edit

class SettingsActivity : AppCompatActivity(), BottomNavigationFragment.BottomNavigationListener {

    lateinit var binding: ActivitySettingsBinding
    lateinit var user: Aluno

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        // Adiciona o fragmento da Bottom Navigation
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, BottomNavigationFragment.newInstance(R.id.settings))
            .commit()

        binding.newEditInfo.setOnClickListener {
            val intent = Intent(this, InfoAlunoActivity::class.java)
            intent.putExtra("aluno", user) // user é do tipo Aluno
            startActivity(intent)
        }

        binding.switchSocialAccount.setOnClickListener {
            toast("Função ainda não implementada", this)
        }

        binding.switchTheme.setOnClickListener {
            toast("Função ainda não implementada", this)
        }

//        val usertoken = getSharedPreferences("user_token", MODE_PRIVATE)
//            .getString("user_token", null)
//        if (usertoken != null) {
//            getUserInfoByToken(usertoken)
//        } else {
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        binding.deleteInfo.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder
                .setTitle("Certeza disso?")
                .setMessage("Tem certeza de que deseja excluir sua conta? Uma vez excluída, não há como voltar atrás.")
                .setPositiveButton("Sim, tenho!") { dialog, which ->
                    deleteUser(user.aluno_id.toString())
                }
                .setNegativeButton("Não") { dialog, which ->
                    // Do something else.
                }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        binding.btnLogout.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder
                .setTitle("Certeza disso?")
                .setMessage("Tem certeza de que deseja deslogar?")
                .setPositiveButton("Sim, tenho!") { dialog, which ->
                    logout()
                }
                .setNegativeButton("Não") { dialog, which ->
                    // Do something else.
                }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    override fun onItemReselected(itemId: Int) {
        when (itemId) {
            R.id.home -> Log.d("MainActivity", "Home foi re-selecionado")
            R.id.settings -> Log.d("MainActivity", "Settings foi re-selecionado")
            else -> Log.d("MainActivity", "Outro item foi re-selecionado")
        }
    }

    fun logout() {
        val sharedPreferences = getSharedPreferences("user_token", MODE_PRIVATE)
        sharedPreferences.edit() {
            remove("user_token")
        }

        toast("Logout realizado com sucesso", this)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun deleteUser(userId: String) {
        val call = RetrofitClient.instance.deleteUser(userId)

        call.enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(
                call: retrofit2.Call<Void>,
                response: retrofit2.Response<Void>
            ) {
                if (response.isSuccessful) {
                    // A conta foi excluída com sucesso
                    toast("Conta excluída com sucesso", this@SettingsActivity)
                    finish() // Fecha a atividade atual
                } else {
                    Log.e("SettingsActivity", "Erro ao excluir conta")
                }
            }

            override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                toast("Erro de conexão", this@SettingsActivity)
            }
        })
    }

    fun getUserInfoByToken(userToken: String) {
        val call = RetrofitClient.instance.verifyTokenAluno(userToken)

        call.enqueue(object : retrofit2.Callback<Aluno> {
            override fun onResponse(
                call: retrofit2.Call<Aluno>,
                response: retrofit2.Response<Aluno>
            ) {

                Log.d("SettingsActivity", "Response: ${response.body()}")
                if (response.isSuccessful) {
                    val userInfo = response.body()
                    user = userInfo!!
                    // Atualize a interface do usuário com as informações do usuário
                    binding.nomeUsuario.text = userInfo.aluno_nome
                    binding.raUsuario.text = userInfo.aluno_id.toString()
                    Glide.with(this@SettingsActivity)
                        .load(userInfo.aluno_foto_url)
                        .placeholder(R.drawable.person_add)
                        .error(R.drawable.manage_accounts)
                        .into(binding.imgProfile)

                } else {
                    toast("Erro ao obter informações do usuário", this@SettingsActivity)
                }
            }

            override fun onFailure(call: retrofit2.Call<Aluno>, t: Throwable) {
                toast("SettingsActivity Erro de conexão: ${t.message}", this@SettingsActivity)
            }
        }
        )
    }
}