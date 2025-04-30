package br.senac.pi4.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.window.Dialog
import br.senac.pi4.R
import br.senac.pi4.databinding.ActivitySettingsBinding
import br.senac.pi4.fragment.BottomNavigationFragment
import br.senac.pi4.models.user.Aluno
import br.senac.pi4.models.user.UserSettings
import br.senac.pi4.retroClient.RetrofitClient
import br.senac.pi4.utils.toast
import com.bumptech.glide.Glide

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

        val userId = intent.getStringExtra("userId") ?: "1142388872"
        getUserInfoById(userId)

        binding.deleteInfo.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder
                .setTitle("Certeza disso?")
                .setMessage("Tem certeza de que deseja excluir sua conta? Uma vez excluída, não há como voltar atrás.")
                .setPositiveButton("Sim, tenho!") { dialog, which ->
                    deleteUser(userId)

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

    fun getUserInfoById(userId: String) {
        val call = RetrofitClient.instance.getUserById(userId)

        call.enqueue(object : retrofit2.Callback<UserSettings> {
            override fun onResponse(
                call: retrofit2.Call<UserSettings>,
                response: retrofit2.Response<UserSettings>
            ) {
                if (response.isSuccessful) {
                    val userInfo = response.body()
                    user = userInfo?.aluno!!
                    // Atualize a interface do usuário com as informações do usuário
                    binding.nomeUsuario.text = userInfo.aluno.aluno_nome
                    binding.raUsuario.text = userInfo.aluno.aluno_id.toString()
                    Glide.with(this@SettingsActivity)
                        .load(userInfo.aluno.aluno_foto_url)
                        .placeholder(R.drawable.person_add)
                        .error(R.drawable.manage_accounts)
                        .into(binding.imgProfile)

                } else {
                    toast("Erro ao obter informações do usuário", this@SettingsActivity)
                }
            }

            override fun onFailure(call: retrofit2.Call<UserSettings>, t: Throwable) {
                toast("SettingsActivity Erro de conexão: ${t.message}", this@SettingsActivity)
            }
        }
        )
    }
}