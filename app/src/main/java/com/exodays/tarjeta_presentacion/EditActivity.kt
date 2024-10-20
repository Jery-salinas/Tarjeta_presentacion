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

        // Configurar el padding para las barras del sistema
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

    // Controla las acciones cuando se selecciona una opción en el menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_send -> {
                if (validateFields()) {
                    showConfirmationDialog()  // Muestra el mensaje de confirmación si los datos son válidos
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

    // Validaciones
    private fun validateFields(): Boolean {
        var isValid = true

        // Validar Nombre (debe tener al menos 3 caracteres y un apellido)
        val name = binding.tieNombre.text.toString().trim()
        if (name.isEmpty() || name.length < 3 || !name.contains(" ")) {
            binding.tilNombre.error = "Nombre inválido, debe incluir un apellido y tener al menos 3 caracteres"
            isValid = false
        } else {
            binding.tilNombre.error = null
        }

        // Validar Correo (debe seguir un formato de correo válido y tener dominios específicos)
        val email = binding.tieCorreo.text.toString().trim()
        val emailPattern = "^[\\w\\.-]+@(gmail|hotmail|uv)\\.(com|mx)\$".toRegex()
        if (email.isEmpty() || !email.matches(emailPattern)) {
            binding.tilCorreo.error = "Correo inválido, solo se acepta @gmail.com, @hotmail.com o @uv.mx"
            isValid = false
        } else {
            binding.tilCorreo.error = null
        }

        // Validar Teléfono (debe tener 10 dígitos numéricos)
        val phone = binding.tieTelefono.text.toString().trim()
        if (phone.isEmpty() || !phone.matches("\\d{10}".toRegex())) {
            binding.tilTelefono.error = "Teléfono inválido, debe tener exactamente 10 dígitos"
            isValid = false
        } else {
            binding.tilTelefono.error = null
        }

        return isValid
    }

    // Mostrar mensaje de confirmación si los datos son válidos
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

    // Limpiar los campos después de confirmar
    private fun clearFields() {
        binding.tieNombre.text?.clear()
        binding.tieCorreo.text?.clear()
        binding.tieTelefono.text?.clear()
    }
}
