package br.com.educacaocrescer.educacaocrescer

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.educacaocrescer.educacaocrescer.databinding.ActivityCadastroCriancaBinding

class CadastroCriancaActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCadastroCriancaBinding.inflate(layoutInflater)
    }

    private var imageUri: Uri? = null

    private lateinit var cameraLauncher: ActivityResultLauncher<Void?>
    private lateinit var galleryLauncher: ActivityResultLauncher<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // 📸 Registrar o launcher da câmera
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            bitmap?.let {
                binding.imgFotoCrianca.setImageBitmap(it)
                // Aqui você pode salvar no Firebase Storage, se quiser
            }
        }

        // 🖼️ Registrar o launcher da galeria
        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.imgFotoCrianca.setImageURI(it)
                imageUri = it
            }
        }

        // 📷 Botão abrir câmera
        binding.btnAbrirCamera.setOnClickListener {
            cameraLauncher.launch(null)
        }

        // 🗂️ Botão abrir galeria
        binding.btnAbrirGaleria.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

    }
}