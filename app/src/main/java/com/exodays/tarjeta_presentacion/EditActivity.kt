package com.exodays.tarjeta_presentacion

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.exodays.tarjeta_presentacion.databinding.ActivityEdit2Binding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEdit2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEdit2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_send -> {
                if (validateFields()) {
                    showConfirmationDialog()
                } else {
                    Toast.makeText(this, "Verifica tus datos!!", Toast.LENGTH_SHORT).show()
                }
                true
            }
            R.id.action_cancel -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Validaciones de los campos
    private fun validateFields(): Boolean {
        var isValid = true

        // Validar Matrícula
        val matricula = binding.tieMatricula.text.toString().trim()
        val matriculaPattern = "^S\\d{8}$".toRegex()
        if (matricula.isEmpty() || !matricula.matches(matriculaPattern)) {
            binding.tilMatricula.error = "Matrícula inválida, debe empezar con 'S' seguido de 8 dígitos"
            isValid = false
        } else {
            binding.tilMatricula.error = null
        }

        // Validar Nombre
        val name = binding.tieNombre.text.toString().trim()
        if (name.isEmpty() || name.length < 3) {
            binding.tilNombre.error = "Nombre inválido, debe tener al menos 3 caracteres"
            isValid = false
        } else {
            binding.tilNombre.error = null
        }

        // Validar Correo
        val email = binding.tieCorreo.text.toString().trim()
        val emailPattern = "^[\\w\\.-]+@(gmail|hotmail|uv)\\.(com|mx)\$".toRegex()
        if (email.isEmpty() || !email.matches(emailPattern)) {
            binding.tilCorreo.error = "Correo inválido, solo se acepta @gmail.com, @hotmail.com o @uv.mx"
            isValid = false
        } else {
            binding.tilCorreo.error = null
        }

        // Validar Teléfono
        val phone = binding.tieTelefono.text.toString().trim()
        if (phone.isEmpty() || !phone.matches("\\d{10}".toRegex())) {
            binding.tilTelefono.error = "Teléfono inválido, debe tener exactamente 10 dígitos"
            isValid = false
        } else {
            binding.tilTelefono.error = null
        }

        // Validar Sitio Web
        val sitioWeb = binding.tieSitioWeb.text.toString().trim()
        val urlPattern = "^https?://[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)+(/[^\\s]*)?\$".toRegex()
        if (sitioWeb.isNotEmpty() && !sitioWeb.matches(urlPattern)) {
            binding.tilSitioWeb.error = "Formato de URL inválido"
            isValid = false
        } else {
            binding.tilSitioWeb.error = null
        }

        // Validar Latitud y Longitud
        val lat = binding.tieLatitud.text.toString().trim()
        val lon = binding.tieLongitud.text.toString().trim()
        try {
            lat.toDouble()
            lon.toDouble()
            binding.tilLatitud.error = null
            binding.tilLongitud.error = null
        } catch (e: NumberFormatException) {
            binding.tilLatitud.error = "Latitud inválida"
            binding.tilLongitud.error = "Longitud inválida"
            isValid = false
        }

        return isValid
    }

    // Mostrar mensaje de confirmación
    private fun showConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Confirmación")
            .setMessage("Datos modificados correctamente")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                clearFields()
            }
            .show()
    }


    private fun clearFields() {
        binding.tieMatricula.text?.clear()
        binding.tieNombre.text?.clear()
        binding.tieCorreo.text?.clear()
        binding.tieTelefono.text?.clear()
        binding.tieSitioWeb.text?.clear()
        binding.tieLatitud.text?.clear()
        binding.tieLongitud.text?.clear()
    }
}
