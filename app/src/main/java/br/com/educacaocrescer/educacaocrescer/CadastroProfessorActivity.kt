package br.com.educacaocrescer.educacaocrescer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.educacaocrescer.educacaocrescer.databinding.ActivityCadastroProfessorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CadastroProfessorActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCadastroProfessorBinding.inflate(layoutInflater)
    }
    private val autenticacao by lazy {
        FirebaseAuth.getInstance()
    }
    private val bancoDados by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        binding.btnCadastrar.setOnClickListener {
            val nome = binding.etNome.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val senha = binding.etSenha.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty() || senha.length < 6) {
                Toast.makeText(this, "Preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            autenticacao.createUserWithEmailAndPassword(email, senha)
                .addOnSuccessListener { result ->
                    val uid = result.user?.uid
                    val dados = hashMapOf(
                        "nome" to nome,
                        "email" to email,
                        "papel" to "professor"
                    )

                    uid?.let {
                        bancoDados.collection("usuarioProfessor")
                            .document(uid)
                            .set(dados)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Professor cadastrado com sucesso!", Toast.LENGTH_LONG).show()
                                finish() // volta para tela anterior
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Erro ao salvar dados no Firestore.", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro ao criar usuário: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

    }
}