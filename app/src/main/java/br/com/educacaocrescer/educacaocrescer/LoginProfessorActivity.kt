package br.com.educacaocrescer.educacaocrescer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.educacaocrescer.educacaocrescer.databinding.ActivityLoginProfessorBinding
import com.google.firebase.auth.FirebaseAuth

class LoginProfessorActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginProfessorBinding.inflate(layoutInflater)
    }
    private val autenticacao by lazy {
        FirebaseAuth.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
//Aqui captura os dados digitados
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.trim()
            val senha = binding.edtSenha.text.trim()


//Aqui testa se os dados foram digitados
            if (email.isEmpty() || senha.isEmpty()){
                Toast.makeText(this, "Preencha todos os campos!!!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
//Aqui faz a autenticação
            autenticacao.signInWithEmailAndPassword(email.toString(), senha.toString())
                .addOnSuccessListener {
                    Toast.makeText(this, "Login realizado com sucesso.", Toast.LENGTH_SHORT).show()
                    startActivity(
                        Intent(this,MainActivity::class.java)
                    )
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro no login: ${it.message}", Toast.LENGTH_SHORT).show()
                    finish()
                }


        }

    }
}