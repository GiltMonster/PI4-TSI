package br.senac.pi4.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.senac.pi4.databinding.ActivityInfoAlunoBinding
import br.senac.pi4.models.user.Aluno
import br.senac.pi4.models.user.AlunoRequest
import br.senac.pi4.models.user.ResponseAluno
import br.senac.pi4.retroClient.RetrofitClient
import br.senac.pi4.utils.toast
import com.google.android.material.snackbar.Snackbar

class InfoAlunoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoAlunoBinding
    lateinit var aluno: Aluno

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInfoAlunoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val aluno = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("aluno", Aluno::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Aluno>("aluno")
        }

        if (aluno != null) {
            binding.editRA.setText(aluno.aluno_id.toString())
            binding.editIdCurso.setText(aluno.curso_id.toString())
            binding.editNome.setText(aluno.aluno_nome)
            binding.editDateSemestre.setText(aluno.aluno_semestre)
            binding.editEmail.setText(aluno.aluno_email)
            binding.editSenha.setText("")
            binding.editGitHub.setText(aluno.aluno_github)
            binding.editInstagram.setText(aluno.aluno_insta)
            binding.editLinkedin.setText(aluno.aluno_linkedin)
            binding.editFotoUrl.setText(aluno.aluno_foto_url)

        } else {
            Log.e("InfoAlunoActivity", "Aluno is null")
        }

        binding.btnAtualizar.setOnClickListener {
            val aluno = AlunoRequest(
                aluno_id = binding.editRA.text.toString(),
                curso_id = 1,
                aluno_nome = binding.editNome.text.toString(),
                aluno_foto_url = binding.editFotoUrl.text.toString(),
                aluno_semestre = binding.editDateSemestre.text.toString(),
                aluno_email = binding.editEmail.text.toString(),
                aluno_senha = binding.editSenha.text.toString(),
                aluno_github = binding.editGitHub.text.toString(),
                aluno_insta = binding.editInstagram.text.toString(),
                aluno_linkedin = binding.editLinkedin.text.toString()
            )

            Log.d("InfoAlunoActivity", "Aluno: $aluno")

            atualizaConta(aluno.aluno_id.toString(), aluno)

        }

        binding.btnCancelar.setOnClickListener {
            finish()
        }
    }

    fun atualizaConta(aluno_id: String, aluno: AlunoRequest){
        val call = RetrofitClient.instance.updateUser(aluno_id, aluno)

        call.enqueue(object : retrofit2.Callback<ResponseAluno> {
            override fun onResponse(
                call: retrofit2.Call<ResponseAluno>,
                response: retrofit2.Response<ResponseAluno>
            ) {
                if (response.isSuccessful) {
                    val alunoAtualizado = response.body()
                    toast("Aluno atualizado com sucesso", this@InfoAlunoActivity)
                } else {
                    toast("Erro ao atualizar aluno", this@InfoAlunoActivity)
                }
            }

            override fun onFailure(call: retrofit2.Call<ResponseAluno>, t: Throwable) {
                Log.e("InfoAlunoActivity", "Falha na requisição: ${t.message}")
                Snackbar.make(binding.root, "Falha na requisição", Snackbar.LENGTH_SHORT).show()
            }
        })


    }
}