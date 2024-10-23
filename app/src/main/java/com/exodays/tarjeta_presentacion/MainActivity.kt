package com.exodays.tarjeta_presentacion

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
        updateUI()

    }

    private val editResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val nombre = it.data?.getStringExtra(getString(R.string.k_nombre))
            val correo = it.data?.getStringExtra(getString(R.string.k_correo))
            val telefono = it.data?.getStringExtra(getString(R.string.k_telefono))
            val sitio = it.data?.getStringExtra(getString(R.string.k_sitioweb))
            latitud = it.data?.getStringExtra(getString(R.string.k_latitud))?.toDouble() ?: 0.0
            longitud = it.data?.getStringExtra(getString(R.string.k_longitud))?.toDouble() ?: 0.0


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