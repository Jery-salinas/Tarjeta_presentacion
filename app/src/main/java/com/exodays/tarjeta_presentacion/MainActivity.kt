package com.exodays.tarjeta_presentacion

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.exodays.tarjeta_presentacion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                val intent = Intent(this, EditActivity::class.java)
                startActivity(intent) // Iniciar la EditActivity
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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