package br.senac.pi4.models.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Aluno(
    val aluno_id: Int,
    val curso_id: Int,
    val aluno_nome: String,
    val aluno_foto_url: String,
    val aluno_semestre: String,
    val aluno_email: String,
    val aluno_github: String,
    val aluno_insta: String,
    val aluno_linkedin: String
): Parcelable

data class AlunoList(
    val alunos: List<Aluno>
)

data class AlunoRequest(
    val aluno_id: String,
    val curso_id: Int,
    val aluno_nome: String,
    val aluno_foto_url: String?,
    val aluno_semestre: String,
    val aluno_email: String,
    val aluno_senha: String,
    val aluno_github: String?,
    val aluno_insta: String?,
    val aluno_linkedin: String?
)

