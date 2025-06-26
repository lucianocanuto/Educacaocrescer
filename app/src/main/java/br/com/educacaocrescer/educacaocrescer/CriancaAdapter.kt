package br.com.educacaocrescer.educacaocrescer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CriancaAdapter(private val lista: List<Crianca>) :
    RecyclerView.Adapter<CriancaAdapter.CriancaViewHolder>() {

    inner class CriancaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgFoto = itemView.findViewById<ImageView>(R.id.imgFoto)
        val txtNome = itemView.findViewById<TextView>(R.id.txtNome)
        val txtTurma = itemView.findViewById<TextView>(R.id.txtTurma)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CriancaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_recycler_crianca, parent, false)
        return CriancaViewHolder(view)
    }

    private var lastPosition = -1

    override fun onBindViewHolder(holder: CriancaViewHolder, position: Int) {
        val crianca = lista[position]
        holder.txtNome.text = crianca.nome
        holder.txtTurma.text = "${crianca.turma} - ${crianca.turno}"

// Usa Glide para carregar a imagem do Firebase
        Glide.with(holder.itemView.context)
            .load(crianca.fotoUrl)
            .placeholder(R.drawable.perfil)
            .into(holder.imgFoto)

        // Fade in animation
        if (position > lastPosition) {
            holder.itemView.alpha = 0f
            holder.itemView.translationY = 50f
            holder.itemView.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(500)
                .start()
            lastPosition = position
        }
    }

    override fun getItemCount() = lista.size
}
