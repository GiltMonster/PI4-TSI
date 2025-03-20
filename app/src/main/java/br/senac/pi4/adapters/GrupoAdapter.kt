package br.senac.pi4.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.senac.pi4.R
import br.senac.pi4.models.grupos.Grupo

class GrupoAdapter(private val listaGrupos: List<Grupo>) : RecyclerView.Adapter<GrupoAdapter.GrupoViewHolder>()  {

    class GrupoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nome_grupo : TextView = view.findViewById(R.id.nome_grupo)
        val desc_grupo : TextView = view.findViewById(R.id.desc_grupo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrupoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grupo, parent, false)
        return GrupoViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrupoViewHolder, position: Int) {
        val grupo = listaGrupos[position]
        holder.nome_grupo.text = grupo.grupo_nome
        holder.desc_grupo.text = grupo.grupo_desc

    }

    override fun getItemCount(): Int {
        return listaGrupos.size
    }

}