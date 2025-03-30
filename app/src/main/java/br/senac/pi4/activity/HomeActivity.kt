package br.senac.pi4.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.senac.pi4.R
import br.senac.pi4.adapters.GrupoAdapter
import br.senac.pi4.fragment.BottomNavigationFragment
import br.senac.pi4.models.grupos.Grupo
import br.senac.pi4.retroClient.RetrofitClient
import br.senac.pi4.utils.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity(), BottomNavigationFragment.BottomNavigationListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        getList()

    }

    override fun onItemReselected(itemId: Int) {
        when (itemId) {
            R.id.home -> getList()
            R.id.settings -> Log.d("MainActivity", "Settings foi re-selecionado")
            else -> Log.d("MainActivity", "Outro item foi re-selecionado")
        }
    }

    private fun getList() {
        val call = RetrofitClient.instance.listarProgetos()

        call.enqueue(object : Callback<List<Grupo>> {
            override fun onResponse(call: Call<List<Grupo>>, response: Response<List<Grupo>>) {
                if (response.isSuccessful) {
                    val listaGrupos = response.body() ?: emptyList()
                    atualizarRecycleView(listaGrupos)
                } else {
                    toast("Erro ao carregar projetos", applicationContext)
                }
            }

            override fun onFailure(call: Call<List<Grupo>>, t: Throwable) {
                toast("Erro de conexão: ${t.message}", applicationContext)
            }
        })

    }

    private fun atualizarRecycleView(listaGrupos: List<Grupo>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recycle_grupos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = GrupoAdapter(listaGrupos)
    }
}