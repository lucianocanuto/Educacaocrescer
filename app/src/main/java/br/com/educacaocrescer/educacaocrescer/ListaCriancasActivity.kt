package br.com.educacaocrescer.educacaocrescer

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.educacaocrescer.educacaocrescer.databinding.ActivityListaCriancasBinding
import com.google.firebase.firestore.FirebaseFirestore

class ListaCriancasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaCriancasBinding
    private val listaCriancas = mutableListOf<Crianca>()
    private lateinit var adapter: CriancaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaCriancasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CriancaAdapter(listaCriancas)
        binding.rvCriancas.layoutManager = LinearLayoutManager(this)
        binding.rvCriancas.adapter = adapter
        val spacing = resources.getDimensionPixelSize(R.dimen.recycler_item_spacing)
        binding.rvCriancas.addItemDecoration(DecoracaoParaRecycler(spacing))

        // 💫 Aplica animação ao carregar os itens do RecyclerView
        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down)
        binding.rvCriancas.layoutAnimation = controller
        binding.rvCriancas.scheduleLayoutAnimation()


        carregarCriancas()
    }

    private fun carregarCriancas() {
        val turmaSelecionada = intent.getStringExtra("turmaSelecionada") ?: return

        FirebaseFirestore.getInstance().collection("criancas")
            .whereEqualTo("turma", turmaSelecionada)
            .get()
            .addOnSuccessListener { docs ->
                listaCriancas.clear()
                for (doc in docs) {
                    val crianca = doc.toObject(Crianca::class.java)
                    listaCriancas.add(crianca)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao buscar crianças", Toast.LENGTH_SHORT).show()
            }
    }
}
