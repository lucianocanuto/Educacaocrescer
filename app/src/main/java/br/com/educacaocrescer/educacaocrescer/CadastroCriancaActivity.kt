package br.com.educacaocrescer.educacaocrescer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.educacaocrescer.educacaocrescer.databinding.ActivityCadastroCriancaBinding

class CadastroCriancaActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCadastroCriancaBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

    }
}