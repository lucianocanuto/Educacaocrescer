package br.com.educacaocrescer.educacaocrescer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.educacaocrescer.educacaocrescer.databinding.ActivityDetalhesCriancaBinding

class DetalhesCriancaActivity : AppCompatActivity() {
    private val biding by lazy {
        ActivityDetalhesCriancaBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(biding.root)

    }
}