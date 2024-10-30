package com.exodays.tarjeta_presentacion

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.result.contract.ActivityResultContracts
import com.exodays.tarjeta_presentacion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var latitud: Double = 0.0
    private var longitud: Double = 0.0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupIntent()
        updateUI()

    }

    fun setupIntent(){
        // Buscar por web
        binding.txtname.setOnClickListener {
            val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                putExtra(SearchManager.QUERY,binding.txtname.text)
            }
            startActivity(intent)
            //launchIntent(intent)
        }

        //Enviar correo
        binding.txtemail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL,binding.txtemail.text.toString())
                putExtra(Intent.EXTRA_SUBJECT,"Asuto TAM")
                putExtra(Intent.EXTRA_TEXT,"Some text here")
            }
            startActivity(intent)
            //launchIntent(intent)
        }

        //Abrir navegador
        binding.txtwebsite.setOnClickListener {
            val  webText = Uri.parse(binding.txtwebsite.text.toString())
            val intent = Intent(Intent.ACTION_VIEW,webText)
            //launchIntent(intent)
            startActivity(intent)
        }

        //Telefono
        binding.txtphone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                val tel = (it as TextView).text
                data = Uri.parse("tel:$tel")
            }
            startActivity(intent)
        }



    }

    private fun launchIntent(intent: Intent){
        if(intent.resolveActivity(packageManager) != null && true){
            startActivity(intent)
        }else{
            Toast.makeText(this,"No se entro la app",Toast.LENGTH_SHORT).show()
        }
    }

    private val editResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val nombre = it.data?.getStringExtra(getString(R.string.k_nombre))
            val correo = it.data?.getStringExtra(getString(R.string.k_correo))
            val telefono = it.data?.getStringExtra(getString(R.string.k_telefono))
            val sitio = it.data?.getStringExtra(getString(R.string.k_sitioweb))
            latitud = it.data?.getStringExtra(getString(R.string.k_latitud))?.toDouble() ?: 0.0
            longitud = it.data?.getStringExtra(getString(R.string.k_longitud))?.toDouble() ?: 0.0

            // Obtener el URI de la imagen si está presente y mostrarla
            val imageUriString = it.data?.getStringExtra(getString(R.string.k_image_uri))
            imageUriString?.let { uriStr ->
                binding.imgprofile.setImageURI(Uri.parse(uriStr))

            }
            updateUI(nombre!!, correo!!, telefono!!, sitio!!)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_edit){
            val intent = Intent(this, EditActivity::class.java)
            // Extraer como recurso
            intent.putExtra(getString(R.string.k_nombre), binding.txtname.text.toString())
            intent.putExtra(getString(R.string.k_correo), binding.txtemail.text.toString())
            intent.putExtra(getString(R.string.k_telefono), binding.txtphone.text.toString())
            intent.putExtra(getString(R.string.k_sitioweb), binding.txtwebsite.text.toString())
            intent.putExtra(getString(R.string.k_longitud),longitud.toString())
            intent.putExtra(getString(R.string.k_latitud),latitud.toString())



            editResult.launch(intent)
        }
        return super.onOptionsItemSelected(item)

    }

    private fun updateUI(
        name: String = "Exodays inc",
        correo: String = "jerrsalinas5@gmail.com",
        telefono: String = "+52 294 181 5097",
        sitio: String = "www.exo.mx"
    ) {
        // Establece los valores de texto en las vistas
        binding.txtname.text = name
        binding.txtemail.text = correo
        binding.txtphone.text = telefono
        binding.txtwebsite.text = sitio
    }
}