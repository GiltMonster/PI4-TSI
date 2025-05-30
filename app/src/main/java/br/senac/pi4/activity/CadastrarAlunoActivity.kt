package br.senac.pi4.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import br.senac.pi4.databinding.ActivityCadastrarAlunoBinding
import br.senac.pi4.models.user.AlunoRequest
import br.senac.pi4.models.user.ResponseAluno
import br.senac.pi4.retroClient.RetrofitClient
import br.senac.pi4.utils.toast
import retrofit2.Call
import retrofit2.Response

class CadastrarAlunoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastrarAlunoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCadastrarAlunoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCadastrar.setOnClickListener {
            cadastrarAluno()
        }

        binding.btnCancelar.setOnClickListener {
            finish()
        }

    }

    fun cadastrarAluno() {

        if ( binding.editRA.text.toString().isEmpty() ||
            binding.editIdCurso.text.toString().isEmpty() ||
            binding.editNome.text.toString().isEmpty() ||
            binding.editDateSemestre.text.toString().isEmpty() ||
            binding.editEmail.text.toString().isEmpty() ||
            binding.editSenha.text.toString().isEmpty()
        ) {
            toast("Preencha todos os campos", this)
            return
        }

        if (binding.editRA.text.toString().toInt() <= 0 ||
            binding.editRA.text.toString().toInt() >= 10) {
            toast("RA inválido", this)
            return
        }


        val aluno = AlunoRequest(
            aluno_id = binding.editRA.text.toString(),
            curso_id = binding.editIdCurso.text.toString().toInt(),
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

        val call = RetrofitClient.instance.createAluno(aluno)

        call.enqueue(object : retrofit2.Callback<ResponseAluno> {
            override fun onResponse(
                call: Call<ResponseAluno>,
                response: Response<ResponseAluno>
            ) {
                if (response.isSuccessful) {
                    toast("Aluno cadastrado com sucesso", this@CadastrarAlunoActivity)
                    finish()
                } else {
                    toast("Erro ao cadastrar aluno", this@CadastrarAlunoActivity)
                }
            }

            override fun onFailure(call: Call<ResponseAluno>, t: Throwable) {
                Log.e("InfoAlunoActivity", "Falha na requisição: ${t.message}")
                Snackbar.make(binding.root, "Falha na requisição", Snackbar.LENGTH_SHORT).show()
            }
        })
    }
}