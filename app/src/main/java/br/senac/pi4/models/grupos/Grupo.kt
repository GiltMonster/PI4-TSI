package br.senac.pi4.models.grupos

data class Grupo(
    val grupo_id: Int,
    val grupo_nome: String,
    val grupo_desc: String,
    val grupo_git_repositorio: String,
    val grupo_tema: String,
    val grupo_video: String
)
