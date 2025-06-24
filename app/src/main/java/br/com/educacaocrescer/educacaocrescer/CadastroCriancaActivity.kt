package br.com.educacaocrescer.educacaocrescer

import android.R
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.com.educacaocrescer.educacaocrescer.databinding.ActivityCadastroCriancaBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.Calendar

class CadastroCriancaActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCadastroCriancaBinding.inflate(layoutInflater)
    }
    private val armazenar by lazy {
        FirebaseStorage.getInstance()
    }
    private val banco by lazy {
        FirebaseFirestore.getInstance()
    }

    private var imageUri: Uri? = null

    private lateinit var abrirCamera: ActivityResultLauncher<Void?>
    private lateinit var abrirGaleria: ActivityResultLauncher<String>
//List dos Spinners
    val turmas = listOf(
        "Berçário 1", "Berçário 2", "Maternal 1",
        "Maternal 2 A", "Maternal 2 B", "Pré-escola"
    )
    val turnos = listOf("Manhã", "Tarde", "Integral")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

//Código dos Spinners

        val adapterTurmas = ArrayAdapter(this, android.R.layout.simple_spinner_item, turmas)
        adapterTurmas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTurma.adapter = adapterTurmas

        val adapterTurnos = ArrayAdapter(this, R.layout.simple_spinner_item, turnos)
        adapterTurnos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTurno.adapter = adapterTurnos


// 📸 Registrar o launcher da câmera
        abrirCamera = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            bitmap?.let {
                binding.imgFotoCrianca.setImageBitmap(it)
                // Aqui você pode salvar no Firebase Storage, se quiser
            }
        }

// 🖼️ Registrar o launcher da galeria
        abrirGaleria = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.imgFotoCrianca.setImageURI(it)
                imageUri = it
            }
        }

// 📷 Botão abrir câmera
        binding.btnAbrirCamera.setOnClickListener {
            abrirCamera.launch(null)
        }

// 🗂️ Botão abrir galeria
        binding.btnAbrirGaleria.setOnClickListener {
            abrirGaleria.launch("image/*")
        }


        binding.btnSalvarCrianca.setOnClickListener {
            val nome = binding.edtNomeCrianca.text.toString().trim()
            val dataNascimento = binding.edtDataNascimento.text.toString().trim()
            val turma = binding.spinnerTurma.selectedItem.toString()
            val turno = binding.spinnerTurno.selectedItem.toString()
            val nomePai = binding.edtNomePai.text.toString().trim()
            val nomeMae = binding.edtNomeMae.text.toString().trim()
            val telefoneEmergencia = binding.edtTelefoneEmergencia.text.toString().trim()

            if (nome.isEmpty() || dataNascimento.isEmpty() || telefoneEmergencia.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (imageUri == null) {
                Toast.makeText(this, "Escolha ou tire uma foto da criança", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Consulta para verificar duplicidade
            banco.collection("criancas")
                .whereEqualTo("telefoneEmergencia", telefoneEmergencia)
                .whereEqualTo("nome", nome)
                .whereEqualTo("dataNascimento", dataNascimento)
                .get()
                .addOnSuccessListener { result ->
                    if (!result.isEmpty) {
                        Toast.makeText(this, "Essa criança já está cadastrada com esse telefone e data de nascimento.", Toast.LENGTH_LONG).show()
                    } else {
                        val anoAtual = Calendar.getInstance().get(Calendar.YEAR)
                        val nomeFoto = "${nome}_${System.currentTimeMillis()}.jpg"
                        val storageRef = armazenar.reference.child("fotos_criancas/$nomeFoto")

                        storageRef.putFile(imageUri!!)
                            .addOnSuccessListener {
                                storageRef.downloadUrl.addOnSuccessListener { fotoUrl ->

                                    val dadosCrianca = hashMapOf(
                                        "nome" to nome,
                                        "dataNascimento" to dataNascimento,
                                        "turma" to turma,
                                        "turno" to turno,
                                        "nomePai" to nomePai,
                                        "nomeMae" to nomeMae,
                                        "telefoneEmergencia" to telefoneEmergencia,
                                        "fotoUrl" to fotoUrl.toString(),
                                        "ano" to anoAtual
                                    )

                                    banco.collection("criancas")
                                        .add(dadosCrianca)
                                        .addOnSuccessListener {
                                            Toast.makeText(this, "Criança cadastrada com sucesso!", Toast.LENGTH_SHORT).show()
                                            finish()
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(this, "Erro ao salvar no banco", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Erro ao enviar imagem", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro ao verificar duplicidade", Toast.LENGTH_SHORT).show()
                }
        }


    }
}