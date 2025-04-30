package br.senac.pi4.models.user

data class ResponseAluno(
    val message: String
) {
    override fun toString(): String {
        return "ResponseAluno(message='$message')"
    }
}