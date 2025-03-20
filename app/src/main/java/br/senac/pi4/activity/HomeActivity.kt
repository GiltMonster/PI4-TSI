package br.senac.pi4.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.senac.pi4.R
import br.senac.pi4.adapters.GrupoAdapter
import br.senac.pi4.models.grupos.Grupo
import br.senac.pi4.retroClient.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        getList()

    }

    private fun getList(){
        val call = RetrofitClient.instance.listarProgetos()
        
        call.enqueue(object : Callback<List<Grupo>> {
            override fun onResponse(call: Call<List<Grupo>>, response: Response<List<Grupo>>) {
                if (response.isSuccessful){
                    val listaGrupos = response.body() ?: emptyList()
                    atualizarRecycleView(listaGrupos)
                }else {
                    Toast.makeText(applicationContext, "Erro ao carregar projetos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Grupo>>, t: Throwable) {
                Toast.makeText(applicationContext, "Erro de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun atualizarRecycleView(listaGrupos: List<Grupo>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recycle_grupos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = GrupoAdapter(listaGrupos)
    }
}