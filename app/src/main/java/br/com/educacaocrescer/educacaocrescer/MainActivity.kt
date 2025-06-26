package br.com.educacaocrescer.educacaocrescer

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.educacaocrescer.educacaocrescer.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        val turmas = listOf("Berçário 1", "Berçário 2", "Maternal 1", "Maternal 2 A", "Maternal 2 B", "Pré-escola")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, turmas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTurmas.adapter = adapter

        binding.btnListarCriancas.setOnClickListener {
            val turmaSelecionada = binding.spinnerTurmas.selectedItem.toString()
            val intent = Intent(this, ListaCriancasActivity::class.java)
            intent.putExtra("turmaSelecionada", turmaSelecionada)
            startActivity(intent)
        }




        binding.btnCadastrarProfessor.setOnClickListener {
            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            AlertDialog.Builder(this)
                .setTitle("Autenticação necessária")
                .setMessage("Digite a senha da diretora para continuar:")
                .setView(input)
                .setPositiveButton("OK") { _, _ ->
                    val senhaDigitada = input.text.toString()
                    val senhaDiretora = "admin123" // Você pode mover isso para o Firebase futuramente

                    if (senhaDigitada == senhaDiretora) {
                        val intent = Intent(this, CadastroProfessorActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Senha incorreta!", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }
        binding.btnCadastrarCrianca.setOnClickListener {
            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            AlertDialog.Builder(this)
                .setTitle("Autenticação necessária")
                .setMessage("Digite a senha da diretora para continuar:")
                .setView(input)
                .setPositiveButton("OK") { _, _ ->
                    val senhaDigitada = input.text.toString()
                    val senhaDiretora = "admin123" // Você pode mover isso para o Firebase futuramente

                    if (senhaDigitada == senhaDiretora) {
                        val intent = Intent(this, CadastroCriancaActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Senha incorreta!", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }



    }
}